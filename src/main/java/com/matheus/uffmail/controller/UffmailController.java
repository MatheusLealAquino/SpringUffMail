package com.matheus.uffmail.controller;

import com.matheus.uffmail.dao.AlunosDao;
import com.matheus.uffmail.model.AppException;
import com.matheus.uffmail.service.AlunoService;
import org.apache.juli.logging.LogFactory;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class UffmailController {

    @Autowired
    private AlunosDao alunos;
    @Autowired
    private AlunoService alunoService;

    private Logger logger = LoggerFactory.getLogger(UffmailController.class);
    
    //Caso de erro de entrada
    @ExceptionHandler(AppException.class)
    public ModelAndView exceptionHandler(AppException e) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("error", e);
        return errorPage;
    }

    // Pagina Inicial
    @GetMapping("/home")
    public String iniciar(Model model) {
        return "index";
    }
    
    //Mostrar todos alunos no banco de dados
    @GetMapping("/listarAlunos")
    public ModelAndView listar(){
        //model.addAttribute("aluno", new Aluno());
        ModelAndView modelAndView = new ModelAndView("listarAlunos");
        
        //Achar todos os alunos cadastrados no banco
        modelAndView.addObject("alunos",alunos.findAll());

        return modelAndView;
    }
    
    //Pagina de criação de UFFMail
    @PostMapping("/uffmail")
    public ModelAndView uffMail(String matricula) throws AppException{
        ModelAndView modelAndView = new ModelAndView("uffmail");
        
        //Retirar espacos em branco do campo da matricula
        matricula = matricula.trim(); 
        
        //Achar aluno e retornar para a view
        modelAndView.addObject("alunos", alunos.findByMatricula(matricula));

        //Fazer checagem se aluno pode fazer UFFMail
        alunoService.getChecar(alunos.findByMatricula(matricula));

        return modelAndView;
    }
    
    //Resposta ao usuario de sucesso
    @PostMapping("/criado")
    public ModelAndView criacao(String matricula, String email){
        ModelAndView modelAndView = new ModelAndView("criado");
        
        //Atualizando o uffMail do aluno a partir do post recebido
        alunoService.atualizarUffMail(matricula,email);
        
        //Achando aluno para retornar seus dados na view
        modelAndView.addObject("alunos",alunos.findByMatricula(matricula)); 

        return modelAndView;
    }

}
