package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.RelatorioDao;
import br.com.fiap.solaraware.dao.SimulacaoDao;
import br.com.fiap.solaraware.dao.UsuarioDao;
import br.com.fiap.solaraware.dto.relatorio.AtualizacaoParcialRelatorioDto;
import br.com.fiap.solaraware.dto.relatorio.AtualizacaoRelatorioDto;
import br.com.fiap.solaraware.dto.relatorio.CadastroRelatorioDto;
import br.com.fiap.solaraware.dto.relatorio.DetalhesRelatorioDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.Relatorio;
import br.com.fiap.solaraware.model.Simulacao;
import br.com.fiap.solaraware.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/relatorios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelatorioResource {
    private RelatorioDao relatorioDao;
    private UsuarioDao usuarioDao;
    private SimulacaoDao simulacaoDao;
    private ModelMapper modelMapper;

    public RelatorioResource() throws Exception{
        relatorioDao = new RelatorioDao(ConnectionFactory.getConnection());
        usuarioDao = new UsuarioDao(ConnectionFactory.getConnection());
        simulacaoDao = new SimulacaoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroRelatorioDto dto, @Context UriInfo uriInfo) throws SQLException {
        Relatorio relatorio = new Relatorio();

        relatorio.setUsuario(new Usuario(dto.getIdUsuario()));
        relatorio.setSimulacao(new Simulacao(dto.getIdSimulacao()));
        relatorio.setTipoRelatorio(dto.getTipoRelatorio());
        relatorio.setDataGeracao(dto.getDataGeracao());

        relatorioDao.cadastrar(relatorio);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(relatorio.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(relatorio, DetalhesRelatorioDto.class)).build();
    }

    @GET
    public List<DetalhesRelatorioDto> listar() throws SQLException{
        return relatorioDao.listar().stream()
                .map(r -> modelMapper.map(r, DetalhesRelatorioDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesRelatorioDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Relatorio relatorio = relatorioDao.pesquisarPorId(id);
        DetalhesRelatorioDto dto = modelMapper.map(relatorio, DetalhesRelatorioDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoRelatorioDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Relatorio relatorio = modelMapper.map(dto, Relatorio.class);

        relatorio.setId(id);
        relatorioDao.atualizar(relatorio);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid AtualizacaoParcialRelatorioDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Relatorio relatorioBanco = relatorioDao.pesquisarPorId(id);
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioDao.pesquisarPorId(dto.getIdSimulacao());
            relatorioBanco.setUsuario(usuario);
        }
        if (dto.getIdSimulacao() != null) {
            Simulacao simulacao = simulacaoDao.pesquisarPorId(dto.getIdSimulacao());
            relatorioBanco.setSimulacao(simulacao);
        }
        if (dto.getTipoRelatorio() != null)
            relatorioBanco.setTipoRelatorio(dto.getTipoRelatorio());
        if (dto.getDataGeracao() != null)
            relatorioBanco.setDataGeracao(dto.getDataGeracao());
        relatorioDao.atualizar(relatorioBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        relatorioDao.excluir(id);
        return Response.noContent().build();
    }
}
