package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.ContatosDao;
import br.com.fiap.solaraware.dto.contatos.AtualizacaoContatosDto;
import br.com.fiap.solaraware.dto.contatos.CadastroContatosDto;
import br.com.fiap.solaraware.dto.contatos.DetalhesContatoDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.Contatos;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/contatos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContatosResource {
    private ContatosDao contatosDao;
    private ModelMapper modelMapper;

    public ContatosResource() throws Exception{
        contatosDao = new ContatosDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroContatosDto dto , @Context UriInfo uriInfo) throws SQLException {
        Contatos contatos = modelMapper.map(dto, Contatos.class);

        contatosDao.cadastrar(contatos);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(contatos.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(contatos, DetalhesContatoDto.class)).build();
    }

    @GET
    public List<DetalhesContatoDto> listar() throws SQLException{
        return contatosDao.listar().stream()
                .map(c -> modelMapper.map(c, DetalhesContatoDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesContatoDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Contatos contatos = contatosDao.pesquisarPorId(id);
        DetalhesContatoDto dto = modelMapper.map(contatos, DetalhesContatoDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoContatosDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Contatos contatos = modelMapper.map(dto, Contatos.class);

        contatos.setId(id);
        contatosDao.atualizar(contatos);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesContatoDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Contatos contatosBanco = contatosDao.pesquisarPorId(id);
        if (dto.getNome() != null)
            contatosBanco.setNome(dto.getNome());
        if (dto.getEmail() != null)
            contatosBanco.setEmail(dto.getEmail());
        if (dto.getTelefone() != null)
            contatosBanco.setTelefone(dto.getTelefone());
        if (dto.getMensagem() != null)
            contatosBanco.setMensagem(dto.getMensagem());
        if (dto.getDataContato() != null)
            contatosBanco.setDataContato(dto.getDataContato());
        contatosDao.atualizar(contatosBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        contatosDao.excluir(id);
        return Response.noContent().build();
    }
}
