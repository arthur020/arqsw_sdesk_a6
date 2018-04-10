package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.FilaService;

@Transactional
@Controller
public class ManterFilaController {
	private FilaService filaService;

	@Autowired
	public ManterFilaController(FilaService filaService) {
		this.filaService = filaService;
	}

	@RequestMapping("/deletar_fila")
	public String deletarFila(Model model) throws IOException {
		model.addAttribute("filas", filaService.listarFilas());
		return "DeleteFila";
	}
	
	@RequestMapping("/form_fila")
	public String formFila() {
		return "CriarFila";
	}
	
	@RequestMapping("/cria_fila")
	public String criarFila( Fila fila, BindingResult results, Model model) {
		try {
			filaService.criar(fila);
			model.addAttribute("fila", fila);
			return "FilaCriada";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}

	@RequestMapping("/delete_fila")
	public String deleteFila(@Valid Fila fila, BindingResult results, Model model) {
		try {
			Fila carregar = filaService.carregar(fila.getId());
			filaService.deletar(carregar);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_filas")
	public String listarFilas(Model model) {
		try {
			List<Fila> filas = filaService.listarFilas();
			model.addAttribute("filas",filas);
			return "ListarFila";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro";
		}
	}
}

