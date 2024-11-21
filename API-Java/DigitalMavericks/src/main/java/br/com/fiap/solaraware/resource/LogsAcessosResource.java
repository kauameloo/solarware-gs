package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.LogsAcessosDao;
import br.com.fiap.solaraware.dao.UsuarioDao;
import br.com.fiap.solaraware.dto.logsAcessos.AtualizacaoLogsAcessosDto;
import br.com.fiap.solaraware.dto.logsAcessos.AtualizacaoParcialLogsAcessosDto;
import br.com.fiap.solaraware.dto.logsAcessos.CadastroLogsAcessosDto;
import br.com.fiap.solaraware.dto.logsAcessos.DetalhesLogsAcessosDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.LogsAcessos;
import br.com.fiap.solaraware.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/logs-acessos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogsAcessosResource {
    private LogsAcessosDao logsAcessosDao;
    private UsuarioDao usuarioDao;
    private ModelMapper modelMapper;

    public LogsAcessosResource() throws Exception{
        logsAcessosDao = new LogsAcessosDao(ConnectionFactory.getConnection());
        usuarioDao = new UsuarioDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroLogsAcessosDto dto , @Context UriInfo uriInfo) throws SQLException {
        LogsAcessos logsAcessos = modelMapper.map(dto, LogsAcessos.class);

        logsAcessosDao.cadastrar(logsAcessos);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(logsAcessos.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(logsAcessos, DetalhesLogsAcessosDto.class)).build();
    }

    @GET
    public List<DetalhesLogsAcessosDto> listar() throws SQLException{
        return logsAcessosDao.listar().stream()
                .map(la -> modelMapper.map(la, DetalhesLogsAcessosDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesLogsAcessosDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        LogsAcessos logsAcessos = logsAcessosDao.pesquisarPorId(id);
        DetalhesLogsAcessosDto dto = modelMapper.map(logsAcessos, DetalhesLogsAcessosDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoLogsAcessosDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        LogsAcessos logsAcessos = modelMapper.map(dto, LogsAcessos.class);

        logsAcessos.setId(id);
        logsAcessosDao.atualizar(logsAcessos);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid AtualizacaoParcialLogsAcessosDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        LogsAcessos logsAcessosBanco = logsAcessosDao.pesquisarPorId(id);
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioDao.pesquisarPorId(dto.getIdUsuario());
            logsAcessosBanco.setUsuario(usuario);
        }
        if (dto.getDataAcesso() != null)
            logsAcessosBanco.setDataAcesso(dto.getDataAcesso());
        if (dto.getAcaoRealizada() != null)
            logsAcessosBanco.setAcaoRealizada(dto.getAcaoRealizada());
        logsAcessosDao.atualizar(logsAcessosBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        logsAcessosDao.excluir(id);
        return Response.noContent().build();
    }
}
