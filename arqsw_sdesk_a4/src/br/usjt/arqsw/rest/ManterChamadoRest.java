package br.usjt.arqsw.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;

@RestController
public class ManterChamadoRest {

	private ChamadoService chamadoService;

	@Autowired
	public ManterChamadoRest(ChamadoService chamadoService) {
		this.chamadoService = chamadoService;
	}
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "rest/inserirChamado")
	public ResponseEntity<Chamado> criarChamado(@RequestBody Chamado chamado) {
		try {
			chamadoService.novoChamado(chamado);
			return new ResponseEntity<Chamado>(chamado, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Chamado>(chamado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "rest/listarChamado/{id}")
	public @ResponseBody ResponseEntity<List<Chamado>> listaChamado(@PathVariable("id") int FilaID) {
		List<Chamado> chamados = null;
		try {
			Fila fila = new Fila();
			fila.setId(FilaID);
			chamados = chamadoService.listarChamados(fila);
			return new ResponseEntity<List<Chamado>>(chamados, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Chamado>>(chamados, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
  	@RequestMapping(method = RequestMethod.PUT, value = "rest/fecharChamado")
	public void fecharChamados(@RequestBody List<Chamado> chamados) {
		try {
			ArrayList<Integer> lista = new ArrayList<>();
			for(Chamado chamado:chamados){
				lista.add(chamado.getNumero());
			}
			chamadoService.fecharChamado(lista);
			System.out.println("atualizado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
