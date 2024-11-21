package br.com.fiap.solaraware.resource;

import br.com.fiap.solaraware.dao.UsuarioDao;
import br.com.fiap.solaraware.dto.usuario.AtualizacaoUsuarioDto;
import br.com.fiap.solaraware.dto.usuario.CadastroUsuarioDto;
import br.com.fiap.solaraware.dto.usuario.DetalhesUsuarioDto;
import br.com.fiap.solaraware.exceptions.IdNaoEncontradoException;
import br.com.fiap.solaraware.factory.ConnectionFactory;
import br.com.fiap.solaraware.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private UsuarioDao usuarioDao;
    private ModelMapper modelMapper;

    public UsuarioResource() throws Exception{
        usuarioDao = new UsuarioDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroUsuarioDto dto , @Context UriInfo uriInfo) throws SQLException {
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        usuarioDao.cadastrar(usuario);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(usuario.getId()));
        return Response.created(builder.build()).entity(modelMapper.map(usuario, DetalhesUsuarioDto.class)).build();
    }

    @GET
    public List<DetalhesUsuarioDto> listar() throws SQLException{
        return usuarioDao.listar().stream()
                .map(u -> modelMapper.map(u, DetalhesUsuarioDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesUsuarioDto pesquisarPorId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Usuario usuario = usuarioDao.pesquisarPorId(id);
        DetalhesUsuarioDto dto = modelMapper.map(usuario, DetalhesUsuarioDto.class);
        return dto;
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizacaoUsuarioDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        usuario.setId(id);
        usuarioDao.atualizar(usuario);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public  Response atualizarParcialmente(@Valid DetalhesUsuarioDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Usuario usuarioBanco = usuarioDao.pesquisarPorId(id);
        if (dto.getNome() != null)
            usuarioBanco.setNome(dto.getNome());
        if (dto.getEmail() != null)
            usuarioBanco.setEmail(dto.getEmail());
        if (dto.getTelefone() != null)
            usuarioBanco.setTelefone(dto.getTelefone());
        if (dto.getDataRegistro() != null)
            usuarioBanco.setDataRegistro(dto.getDataRegistro());
        usuarioDao.atualizar(usuarioBanco);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        usuarioDao.excluir(id);
        return Response.noContent().build();
    }
}
