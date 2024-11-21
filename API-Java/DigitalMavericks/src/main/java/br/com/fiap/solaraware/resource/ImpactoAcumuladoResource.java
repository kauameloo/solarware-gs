package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.ImpactoAcumuladoDao;
import br.com.fiap.solaraware.dto.impactoAcumulado.AtualizacaoImpactoAcumuladoDto;
import br.com.fiap.solaraware.dto.impactoAcumulado.CadastroImpactoAcumuladoDto;
import br.com.fiap.solaraware.dto.impactoAcumulado.DetalhesImpactoAcumuladoDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.ImpactoAcumulado;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/impactos-acumulados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImpactoAcumuladoResource {
    private ImpactoAcumuladoDao impactoAcumuladoDao;
    private ModelMapper modelMapper;

    public ImpactoAcumuladoResource() throws Exception{
        impactoAcumuladoDao = new ImpactoAcumuladoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroImpactoAcumuladoDto dto , @Context UriInfo uriInfo) throws SQLException {
        ImpactoAcumulado impactoAcumulado = modelMapper.map(dto, ImpactoAcumulado.class);

        impactoAcumuladoDao.cadastrar(impactoAcumulado);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(impactoAcumulado.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(impactoAcumulado, DetalhesImpactoAcumuladoDto.class)).build();
    }

    @GET
    public List<DetalhesImpactoAcumuladoDto> listar() throws SQLException{
        return impactoAcumuladoDao.listar().stream()
                .map(ia -> modelMapper.map(ia, DetalhesImpactoAcumuladoDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesImpactoAcumuladoDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        ImpactoAcumulado impactoAcumulado = impactoAcumuladoDao.pesquisarPorId(id);
        DetalhesImpactoAcumuladoDto dto = modelMapper.map(impactoAcumulado, DetalhesImpactoAcumuladoDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoImpactoAcumuladoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ImpactoAcumulado impactoAcumulado = modelMapper.map(dto, ImpactoAcumulado.class);

        impactoAcumulado.setId(id);
        impactoAcumuladoDao.atualizar(impactoAcumulado);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesImpactoAcumuladoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        ImpactoAcumulado impactoAcumuladoBanco = impactoAcumuladoDao.pesquisarPorId(id);
        if (dto.getDataRegistro() != null)
            impactoAcumuladoBanco.setDataRegistro(dto.getDataRegistro());
        if (dto.getEmissaoReduzidaTotal() != null)
            impactoAcumuladoBanco.setEmissaoReduzidaTotal(dto.getEmissaoReduzidaTotal());
        if (dto.getEconomiaTotal() != null)
            impactoAcumuladoBanco.setEconomiaTotal(dto.getEconomiaTotal());
        if (dto.getBeneficioAmbiental() != null)
            impactoAcumuladoBanco.setBeneficioAmbiental(dto.getBeneficioAmbiental());
        impactoAcumuladoDao.atualizar(impactoAcumuladoBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        impactoAcumuladoDao.excluir(id);
        return Response.noContent().build();
    }
}
