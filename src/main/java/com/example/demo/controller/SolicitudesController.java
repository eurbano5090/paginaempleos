package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Solicitud;
import com.example.demo.models.Usuario;
import com.example.demo.models.Vacante;
import com.example.demo.service.ICategoriaService;
import com.example.demo.service.ISolicitudService;
import com.example.demo.service.IUsuarioService;
import com.example.demo.service.IVacanteService;
import com.example.demo.util.Util;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	    @Autowired
	    private IVacanteService ivaSe;
	
	    @Value("${demo2.ruta.cv}")
	    private String rutaCv;
	
	    @Autowired
	   	private IUsuarioService serviceUsuario;
	    
	    @Autowired
		private ISolicitudService solicitudesService;
	    
		
		@Autowired
		private ICategoriaService icat;
	    
	    
	    @GetMapping("/indexPaginate")
		public String mostrarIndexPaginado(Model model, @PageableDefault(sort= {"id"},direction=Sort.Direction.DESC, size=20) Pageable page) {
			Page<Solicitud> lista = solicitudesService.buscarTodas(page);
			model.addAttribute("solicitudes", lista);
			return "solicitudes/listSolicitudes";
		}
	
	    @GetMapping("/index")
		public String mostrarIndex(Model model) {
	    	List<Solicitud> lista = solicitudesService.mostrarTodas();
	    	model.addAttribute("solicitudes", lista);
			return "solicitudes/listSolicitudes";
		}
	    
	    
	@GetMapping("/create/{idVacante}")
	public String crear(Solicitud solicitud,@PathVariable("idVacante")Integer idVacante,Model model) {
		Vacante vacante=ivaSe.buscarPorId(idVacante);
		System.out.println("idVacante :"+ idVacante);
		model.addAttribute("vacante",vacante);
		return "solicitudes/formSolicitud";
		
	}
	
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication) {	
		
		// Recuperamos el username que inicio sesión
		String username = authentication.getName();
		System.out.println("el usuario es:" +username);
		
		if (result.hasErrors()){
			
			System.out.println("Existieron errores");
			return "solicitudes/formSolicitud";
		}	
		
		if (!multiPart.isEmpty()) {
		
			String nombreArchivo = Util.guardarArchivo(multiPart, rutaCv);
			if (nombreArchivo!=null){ // El archivo (CV) si se subio				
				solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
			}	
		}

		// Buscamos el objeto Usuario en BD	
		Usuario usuario = serviceUsuario.buscarPorUsername(username);				
		solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario
		
		// Guadamos el objeto solicitud en la bd
		solicitudesService.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");
			
		System.out.println("Solicitud:" + solicitud);
		return "redirect:/";		
	}
	
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes) {
		
		// Eliminamos la solicitud.
		solicitudesService.eliminar(idSolicitud);
			
		attributes.addFlashAttribute("msg", "La solicitud fue eliminada!.");
		//return "redirect:/solicitudes/index";
		return "redirect:/solicitudes/indexPaginate";
	}
	
	 @ModelAttribute
     public void setGenericos(Model model) {
    
     model.addAttribute("vacantes",ivaSe.buscarDestacadas());
     model.addAttribute("categorias",icat.mostrarTodas());
  
}
}