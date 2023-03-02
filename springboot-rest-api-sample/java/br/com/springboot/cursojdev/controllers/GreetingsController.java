package br.com.springboot.cursojdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.cursojdev.model.Usuario;
import br.com.springboot.cursojdev.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired //IC-CD-CDI - injeção de dependencia
	private UsuarioRepository usuarioRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornarOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	return "ola mundo"+nome;
    	
    }
    
    @GetMapping(value = "listatodos") //metodo de API
    @ResponseBody //retorna os dados para o corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); //faz a consulta no BD
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //retorna a lista em um JSON
    	
    }
    
    @PostMapping(value = "salvar") //mapeia a url
    @ResponseBody  //descricao da resposta
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){  //recebe os dados para salvar
    	
      Usuario user = usuarioRepository.save(usuario);
      return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    }
    
    @PutMapping(value = "atualizar") //mapeia a url
    @ResponseBody  //descricao da resposta
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){  //recebe os dados para salvar
    	
      if(usuario.getId()==null) {
    	  return new ResponseEntity<String>("Id nao foi informado", HttpStatus.OK); 
      }	
    	
      Usuario user = usuarioRepository.saveAndFlush(usuario);
      return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }  
    
    
    @DeleteMapping(value = "deletar") //mapeia a url
    @ResponseBody  //descricao da resposta
    public ResponseEntity<String> delete (@RequestParam(name = "idUser") Long idUser){  //recebe os dados para salvar
    	
      usuarioRepository.deleteById(idUser);
      return new ResponseEntity<String>("user deletado com sucesso", HttpStatus.OK);
    	
    }
       
    @GetMapping(value = "buscaruserid") //mapeia a url
    @ResponseBody  //descricao da resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "idUser") Long idUser){  //recebe os dados para consultar
    	
     Usuario usuario = usuarioRepository.findById(idUser).get();
      return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    }   
    
    
    @GetMapping(value = "buscarPorNome") //mapeia a url
    @ResponseBody  //descricao da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name){  //recebe os dados para consultar
    	
     List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
      return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    	
    }      
    
    
    
    
}
