package com.matheus.uffmail.service;

import com.matheus.uffmail.dao.AlunosDao;
import com.matheus.uffmail.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlunoServiceTest {

    AlunoService alunoService = new AlunoService();
    AlunosDao alunosDao;
    Aluno aluno = new Aluno();

    @Before
    public void setUp() {
        alunosDao = mock(AlunosDao.class);
        alunoService = new AlunoService().setMock(alunosDao);
    }


    @Test
    public void teste_gerarUffMail(){
        aluno.setNome("Matheus Leal");

        ArrayList<String> emails = aluno.getGerarUffMail();
        Assert.assertTrue(emails.stream().collect(Collectors.toSet()).contains("matheusleal@id.uff.br"));
    }

    //Quando aluno esta Ativo e não possui UffMail
    @Test
    public void teste_checar1(){
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Ativo");
        aluno.setUffMail("");
        int resposta = alunoService.getChecar(aluno);

        Assert.assertTrue("Quando coloco aluno como ativo espera resultado 4, nessa obteve: " + resposta, resposta == 4 );
    }

    //Quando aluno esta Invativo e não possui UffMail
    @Test
    public void teste_checar2(){
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Inativo");
        aluno.setUffMail("");
        int resposta = alunoService.getChecar(aluno);

        Assert.assertTrue("Quando coloco aluno como inativo espera resultado 2, nessa obteve: " + resposta, resposta == 2 );
    }


    //Quando aluno esta Ativo e possui UffMail
    @Test
    public void teste_checar3(){
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Ativo");
        aluno.setUffMail("teste@id.uff.br");
        int resposta = alunoService.getChecar(aluno);

        Assert.assertTrue("Quando coloco aluno como ativo e com uffmail espera resultado 1, nessa obteve: " + resposta, resposta == 1 );
    }

    @Test
    public void teste_checar4(){
        aluno = null;

        int resposta = alunoService.getChecar(aluno);

        Assert.assertTrue("Quando coloco aluno como null espera resultado 0, nessa obteve: " + resposta, resposta == 0 );
    }


    @Test
    @Transactional
    public void teste_atualizarUffMail(){

        String matricula = "109647";
        String email = "teste@id.uff.br";

        when(alunosDao.findByMatricula(eq(matricula))).thenReturn(aluno);
        int resposta = alunoService.atualizarUffMail(matricula,email);

        Assert.assertTrue("Quando coloco aluno como ativo e com uffmail espera resultado 1, nessa obteve: ", resposta == 1 );
    }

}
