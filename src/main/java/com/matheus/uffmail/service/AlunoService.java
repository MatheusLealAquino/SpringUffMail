package com.matheus.uffmail.service;

import com.matheus.uffmail.dao.AlunosDao;
import com.matheus.uffmail.model.Aluno;
import com.matheus.uffmail.model.AppException;
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

    public void getChecar(Aluno aluno) throws AppException{
        if(aluno == null) { //Entrada foi diferente do padrão ou aluno não encontrado
            throw new AppException("Entrada com matricula errada!");
        }else if (!aluno.getUffMail().equals("")) { //Aluno(a) já possui UFFMail;
            throw new AppException("Aluno(a) "+aluno.getNome()+" já possui UFFMail!");
        } else if (!aluno.getStatus().equals("Ativo")) { //Aluno(a) se encontra inativo;
            throw new AppException("Aluno(a) "+aluno.getNome()+" se encontra inativo!");
        }
    } 

}
