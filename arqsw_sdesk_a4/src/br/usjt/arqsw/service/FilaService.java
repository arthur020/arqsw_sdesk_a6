package br.usjt.arqsw.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.usjt.arqsw.dao.FilaDAO;
import br.usjt.arqsw.entity.Fila;

@Service
public class FilaService {
	private FilaDAO dao;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	public FilaService(FilaDAO dao) {
		this.dao = dao;
	}
	
	public List<Fila> listarFilas() throws IOException{
		return dao.listarFilas();
	}
	
	public Fila carregar(int id) throws IOException{
		return dao.carregar(id);
	}
	
	public int criar(Fila fila) throws IOException{
		return dao.criar(fila);
	}
	
	public void deletar(Fila fila) throws IOException{
		dao.deletar(fila);
	}
	
	public void gravarImagem(ServletContext servletContect, Fila fila, MultipartFile file) throws IOException{
		if(!file.isEmpty()) {
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			String path = servletContect.getRealPath(servletContect.getContextPath());
			path = path.substring(0,  path.lastIndexOf('/'));
			String nomeArquivo = "img"+fila.getId()+".jpg";
			fila.setFigura(nomeArquivo);
			criar(fila);
			File destination = new File(path + File.separatorChar + "img" + File.separatorChar + nomeArquivo);
			if(destination.exists()) {
				destination.delete();
			}		
			ImageIO.write(src, "jpg", destination);
		}
	}
}
