package com.matheus.uffmail.service;

import com.matheus.uffmail.dao.AlunosDao;
import com.matheus.uffmail.model.Aluno;
import com.matheus.uffmail.model.AppException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.junit.Rule;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlunoServiceTest {

    AlunoService alunoService = new AlunoService();
    AlunosDao alunosDao;
    Aluno aluno = new Aluno();
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none(); 

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
    public void teste_checar1() throws AppException{
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Ativo");
        aluno.setUffMail("");
        
        alunoService.getChecar(aluno);
    }

    //Quando aluno esta Invativo e não possui UffMail
    @Test
    public void teste_checar2() throws AppException{
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Inativo");
        aluno.setUffMail("");
        
        String esperado = "Aluno(a) "+aluno.getNome()+" se encontra inativo!";
        expectedException.expect(AppException.class);
        expectedException.expectMessage(esperado);
        
        alunoService.getChecar(aluno);
    }


    //Quando aluno esta Ativo e possui UffMail
    @Test
    public void teste_checar3() throws AppException{
        aluno.setNome("Matheus Leal");
        aluno.setStatus("Ativo");
        aluno.setUffMail("teste@id.uff.br");
        
        String esperado = "Aluno(a) "+aluno.getNome()+" já possui UFFMail!";
        expectedException.expect(AppException.class);
        expectedException.expectMessage(esperado);
        
        alunoService.getChecar(aluno);
    }
    
    //Aluno não encontrado
    @Test
    public void teste_checar4() throws AppException{
        aluno = null;
        
        String esperado = "Entrada com matricula errada!";
        expectedException.expect(AppException.class);
        expectedException.expectMessage(esperado);
        
        
        alunoService.getChecar(aluno);
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
