package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Perfil;
import com.example.demo.models.Usuario;
import com.example.demo.models.Vacante;
import com.example.demo.service.ICategoriaService;
import com.example.demo.service.IUsuarioService;
import com.example.demo.service.IVacanteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private IVacanteService iva;
	
	@Autowired
	private IUsuarioService iusu;
	
	@Autowired
	private ICategoriaService icat;
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication authentication, HttpSession session) {		
		
		String username = authentication.getName();		
		for(GrantedAuthority rol: authentication.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		
		if (session.getAttribute("usuario") == null){
			Usuario usuario = iusu.buscarPorUsername(username);	
			session.setAttribute("usuario", usuario);
		}
		
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		
		String pwdPlano = usuario.getPassword();
		
		String pwdEncriptado = passwordEncoder.encode(pwdPlano); 
		
		usuario.setPassword(pwdEncriptado);	
		usuario.setEstatus(1); 
		usuario.setFechaRegistro(new Date()); 
		
		
		Perfil perfil = new Perfil();
		perfil.setId(3); 
		usuario.agregar(perfil);
		
		iusu.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "Has sido registrado. ¡Ahora puedes ingresar al sistema!");
		
		return "redirect:/login";
	}
	

	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante,Model model) {
		System.out.println("buscando por:" + vacante);
		vacante.setEstatus("Aprobada");
		ExampleMatcher matcher= ExampleMatcher.
				matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.
						contains());
	
		Example<Vacante> exam=Example.of(vacante,matcher);
		List<Vacante>filtro=iva.buscarByExample(exam);
		model.addAttribute("vacantes",filtro);
		return "home";
		
	}
    
    
    @GetMapping("/login")
 	public String mostrarLogin() {
 		return "formLogin";
 	}
    
    
    @GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}
	
	
	 @ModelAttribute
     public void setGenericos(Model model) {
     Vacante vacanteSearch=new Vacante();
     vacanteSearch.reset();
     model.addAttribute("vacantes", iva.buscarDestacadas());
     model.addAttribute("categorias",icat.mostrarTodas());
     model.addAttribute("search", vacanteSearch);
	 }
	 
	 @GetMapping("/bcrypt/{texto}")
	 @ResponseBody
	 public String encriptar(@PathVariable("texto") String texto) {
		 return texto +" encriptado en Bcrypt :" + passwordEncoder.encode(texto);
	 }
	 
	 @InitBinder
     public void setGenerico(WebDataBinder binder) {
	  binder.registerCustomEditor(String.class,new StringTrimmerEditor(true));
	   
   }
	 
	 
	 
}
	

