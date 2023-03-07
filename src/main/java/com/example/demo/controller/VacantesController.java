package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Vacante;
import com.example.demo.service.ICategoriaService;
import com.example.demo.service.IVacanteService;
import com.example.demo.util.Util;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${demo2.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IVacanteService iva;
	
	@Autowired
	private ICategoriaService icat;
	
	@GetMapping("/crear")
	public String crear(@ModelAttribute Vacante vacante) {
	
		return "vacantes/formVacante";
	}
	
	
	@PostMapping("/save")
	public String guardar(@ModelAttribute Vacante vacante,BindingResult result,RedirectAttributes attributes,Model model,
			@RequestParam("archivoImagen") MultipartFile multiPart ) {
		if(result.hasErrors()) {
		
				System.out.println("Ocurrio un errores");
		
			return"vacantes/formVacante";
		}
		if (!multiPart.isEmpty()) {
		
		
			String nombreImagen = Util.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ 
			
			vacante.setImagen(nombreImagen);
			}
		iva.guardar(vacante);
		attributes.addFlashAttribute("msg","Registro Guardado");
		System.out.println("Vacante"+ vacante);
	
		}return "redirect:/vacantes/indexPaginate";
		}
	
	@GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idVacante,Model model) {
		Vacante vacante = iva.buscarPorId(idVacante);
		model.addAttribute("vacante",vacante);
	
		return "vacantes/formVacante";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante>lista=iva.findAll();
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping(value="/indexPaginate")
	public String mostrarIndexPaginado(Model model,Pageable page) {
		Page<Vacante>lista=iva.buscarTodas(page);
		model.addAttribute("vacantes",lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante,RedirectAttributes attributes) {
		System.out.println("borrando vacante con id:" + idVacante);
		iva.eliminar(idVacante);
		attributes.addFlashAttribute("msg","La vacante fue eliminada");
		return "redirect:/vacantes/indexPaginate";
		
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante,Model model) {
		
		Vacante vacante=iva.buscarPorId(idVacante);
		
		System.out.println("IdVacante:" + idVacante);
		model.addAttribute("vacante",vacante);
		
		return "detalle";
		
		
	}
	
	@ModelAttribute
	public void setGenericos(Model model){
		model.addAttribute("categorias", icat.mostrarTodas());
	}
}
