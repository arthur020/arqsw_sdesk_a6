package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;
import br.usjt.arqsw.service.FilaService;

@Transactional
@Controller
public class ManterChamadosController {
	private FilaService filaService;
	private ChamadoService chamadoService;

	@Autowired
	public ManterChamadosController(FilaService fs, ChamadoService cs) {
		filaService = fs;
		chamadoService = cs;
	}

	@RequestMapping("index")
	public String inicio() {
		return "index";
	}
	
	private List<Fila> listarFilas() throws IOException{
			return filaService.listarFilas();
	}
	
	
	@RequestMapping("/listar_filas_exibir")
	public String listarFilasExibir(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "ChamadoListar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_filas_chamado_fechar")
	public String listarFilasChamadoFechar(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "ChamadoFilaFechar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_chamados_exibir")
	public String listarChamadosExibir(@Valid Fila fila, BindingResult result, Model model) {
		try {
			if (result.hasFieldErrors("id")) {
				model.addAttribute("filas", listarFilas());
				System.out.println("Deu erro " + result.toString());
				return "ChamadoListar";
			}
			fila = filaService.carregar(fila.getId());
			model.addAttribute("fila", fila);

			List<Chamado> chamados = chamadoService.listarChamados(fila);
			model.addAttribute("chamados", chamados);
			
			return "ChamadoListarExibir";

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@RequestMapping("/listar_chamados_abertos")
	public String listarChamadosAbertos(@Valid Fila fila, BindingResult result, Model model) {
		try {
			if (result.hasFieldErrors("id")) {
				model.addAttribute("filas", listarFilas());
				System.out.println("Deu erro " + result.toString());
				return "ChamadoListar";
			}
			fila = filaService.carregar(fila.getId());
			model.addAttribute("fila", fila);

			List<Chamado> chamados = chamadoService.listarChamadosAbertos(fila);
			model.addAttribute("chamados", chamados);
			
			return "ChamadoAbertosListar";

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	
	@Transactional
  	@RequestMapping("/fechar_chamado/{id}")
	public String fecharChamados(@PathVariable("id") int numero) {
		try {
			ArrayList<Integer> lista = new ArrayList<>();
				lista.add(numero);
			chamadoService.fecharChamado(lista);
			return "index";
		} catch (IOException e) {
			e.printStackTrace();
			return "erro";
		}
	}

}
