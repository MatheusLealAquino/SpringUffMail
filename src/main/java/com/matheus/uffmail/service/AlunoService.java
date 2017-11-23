package com.matheus.uffmail.service;

import com.matheus.uffmail.dao.AlunosDao;
import com.matheus.uffmail.model.Aluno;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunosDao alunos;
    Logger logger = LoggerFactory.getLogger(AlunoService.class);

    public AlunoService setMock(AlunosDao alunosDao){
        this.alunos = alunosDao;
        return this;
    }

    @Transactional
    public int atualizarUffMail(String matricula, String email) {
        Aluno aluno = alunos.findByMatricula(matricula);
        aluno.setUffMail(email);
        return 1;
    }

    public Integer getChecar(Aluno aluno){
        int resultado = 0;
        
        if(aluno == null) { //Entrada foi diferente do padrão ou aluno não encontrado
            return 0; 
        }else if (!aluno.getUffMail().equals("")) { //Aluno(a) já possui UFFMail;
            resultado += 1;
        } else if (!aluno.getStatus().equals("Ativo")) { //Aluno(a) se encontra inativo;
            resultado +=  2; 
        } else { //Tudo certo
            return 4; 
        }
        return resultado; //Se return ter resultado igual a 3, aluno esta inativo e já possui uffMail
    }

}
