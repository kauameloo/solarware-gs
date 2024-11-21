package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.ConfiguracoesPlataformaDao;
import br.com.fiap.solaraware.dto.configuracoesPlataforma.AtualizacaoConfiguracoesPlataformaDto;
import br.com.fiap.solaraware.dto.configuracoesPlataforma.CadastroConfiguracoesPlataformaDto;
import br.com.fiap.solaraware.dto.configuracoesPlataforma.DetalhesConfiguracoesPlataformaDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.ConfiguracoesPlataforma;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/configuracoes-plataforma")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfiguracoesPlataformaResource {
    private ConfiguracoesPlataformaDao configuracoesPlataformaDao;
    private ModelMapper modelMapper;

    public ConfiguracoesPlataformaResource() throws Exception{
        configuracoesPlataformaDao = new ConfiguracoesPlataformaDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroConfiguracoesPlataformaDto dto , @Context UriInfo uriInfo) throws SQLException {
        ConfiguracoesPlataforma configuracoesPlataforma = modelMapper.map(dto, ConfiguracoesPlataforma.class);

        configuracoesPlataformaDao.cadastrar(configuracoesPlataforma);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(configuracoesPlataforma.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(configuracoesPlataforma, DetalhesConfiguracoesPlataformaDto.class)).build();
    }

    @GET
    public List<DetalhesConfiguracoesPlataformaDto> listar() throws SQLException{
        return configuracoesPlataformaDao.listar().stream()
                .map(cp -> modelMapper.map(cp, DetalhesConfiguracoesPlataformaDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesConfiguracoesPlataformaDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        ConfiguracoesPlataforma configuracoesPlataforma = configuracoesPlataformaDao.pesquisarPorId(id);
        DetalhesConfiguracoesPlataformaDto dto = modelMapper.map(configuracoesPlataforma, DetalhesConfiguracoesPlataformaDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoConfiguracoesPlataformaDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ConfiguracoesPlataforma configuracoesPlataforma = modelMapper.map(dto, ConfiguracoesPlataforma.class);

        configuracoesPlataforma.setId(id);
        configuracoesPlataformaDao.atualizar(configuracoesPlataforma);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesConfiguracoesPlataformaDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ConfiguracoesPlataforma configuracoesPlataformaBanco = configuracoesPlataformaDao.pesquisarPorId(id);
        if (dto.getDescricao() != null)
            configuracoesPlataformaBanco.setDescricao(dto.getDescricao());
        if (dto.getValor() != null)
            configuracoesPlataformaBanco.setValor(dto.getValor());
        if (dto.getDataAtualizacao() != null)
            configuracoesPlataformaBanco.setDataAtualizacao(dto.getDataAtualizacao());
        configuracoesPlataformaDao.atualizar(configuracoesPlataformaBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        configuracoesPlataformaDao.excluir(id);
        return Response.noContent().build();
    }
}
