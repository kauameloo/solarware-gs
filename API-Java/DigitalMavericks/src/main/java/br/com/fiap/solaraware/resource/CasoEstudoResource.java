package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.CasoEstudoDao;
import br.com.fiap.solaraware.dto.casoEstudo.AtualizacaoCasoEstudoDto;
import br.com.fiap.solaraware.dto.casoEstudo.CadastroCasoEstudoDto;
import br.com.fiap.solaraware.dto.casoEstudo.DetalhesCasoEstudoDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.CasoEstudo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/casos-estudo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CasoEstudoResource {
    private CasoEstudoDao casoEstudoDao;
    private ModelMapper modelMapper;

    public CasoEstudoResource() throws Exception{
        casoEstudoDao = new CasoEstudoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroCasoEstudoDto dto , @Context UriInfo uriInfo) throws SQLException {
        CasoEstudo casoEstudo = modelMapper.map(dto, CasoEstudo.class);

        casoEstudoDao.cadastrar(casoEstudo);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(casoEstudo.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(casoEstudo, DetalhesCasoEstudoDto.class)).build();
    }

    @GET
    public List<DetalhesCasoEstudoDto> listar() throws SQLException{
        return casoEstudoDao.listar().stream()
                .map(ce -> modelMapper.map(ce, DetalhesCasoEstudoDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesCasoEstudoDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        CasoEstudo casoEstudo = casoEstudoDao.pesquisarPorId(id);
        DetalhesCasoEstudoDto dto = modelMapper.map(casoEstudo, DetalhesCasoEstudoDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoCasoEstudoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        CasoEstudo casoEstudo = modelMapper.map(dto, CasoEstudo.class);

        casoEstudo.setId(id);
        casoEstudoDao.atualizar(casoEstudo);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesCasoEstudoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        CasoEstudo casoEstudoBanco = casoEstudoDao.pesquisarPorId(id);
        if (dto.getLocalizacao() != null)
            casoEstudoBanco.setLocalizacao(dto.getLocalizacao());
        if (dto.getDescricao() != null)
            casoEstudoBanco.setDescricao(dto.getDescricao());
        if (dto.getEmissaoReduzida() != null)
            casoEstudoBanco.setEmissaoReduzida(dto.getEmissaoReduzida());
        if (dto.getEconomiaGerada() != null)
            casoEstudoBanco.setEconomiaGerada(dto.getEconomiaGerada());
        if (dto.getBeneficiosAmbientais() != null)
            casoEstudoBanco.setBeneficiosAmbientais(dto.getBeneficiosAmbientais());
        casoEstudoDao.atualizar(casoEstudoBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        casoEstudoDao.excluir(id);
        return Response.noContent().build();
    }
}
