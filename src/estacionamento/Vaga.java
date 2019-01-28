/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento;

import java.io.Serializable;
import java.util.ArrayList;


public class Vaga implements Serializable {
    private int ID;
    private float limitePeso;
    private float limiteAltura;
    private float limiteComprimento;
    private float limiteLargura;
    private int vezesUsada;
    private int falhasUso;
    private boolean disponivel;
    ArrayList<Veiculo> veiculosEstacionados;
    
    public Vaga(int ID, float limitePeso, float limiteAltura, float limiteComprimento, float limiteLargura) {
        this.ID = ID;
        this.limitePeso = limitePeso;
        this.limiteAltura = limiteAltura;
        this.limiteComprimento = limiteComprimento;
        this.limiteLargura = limiteLargura;
        this.vezesUsada = 0;
        this.falhasUso = 0;
        this.disponivel = true;
        this.veiculosEstacionados= new ArrayList();
    }
    
    public Vaga(int ID, float limitePeso, float limiteAltura, float limiteComprimento, float limiteLargura,boolean disp) {
        this.ID = ID;
        this.limitePeso = limitePeso;
        this.limiteAltura = limiteAltura;
        this.limiteComprimento = limiteComprimento;
        this.limiteLargura = limiteLargura;
        this.vezesUsada = 0;
        this.falhasUso = 0;
        this.disponivel = disp;
        this.veiculosEstacionados= new ArrayList();
    }
    
    public int getVezesUsada() {
        return vezesUsada;
    }

    public void incrementaVezesUsada() {
        this.vezesUsada = this.vezesUsada+1;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public float getLimiteLargura() {
        return limiteLargura;
    }

    public void setLimiteLargura(float limiteLargura) {
        this.limiteLargura = limiteLargura;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getLimitePeso() {
        return limitePeso;
    }

    public void setLimitePeso(float limitePeso) {
        this.limitePeso = limitePeso;
    }

    public float getLimiteAltura() {
        return limiteAltura;
    }

    public void setLimiteAltura(float limiteAltura) {
        this.limiteAltura = limiteAltura;
    }

    public float getLimiteComprimento() {
        return limiteComprimento;
    }

    public int getFalhasUso() {
        return falhasUso;
    }
    
    public void setLimiteComprimento(float limiteComprimento) {
        this.limiteComprimento = limiteComprimento;
    }

    public void incVezesUsada() {
        this.vezesUsada = vezesUsada+1;
    }

    public void incFalhasUso() {
        this.falhasUso = falhasUso+1;
    }
    
    //Carrega para um Array List a lista de vagas presente na tabela.
    public static ArrayList<Vaga> carregaDaTabelaVagas(Visual visual){
        ManipulaTabelas manip = new ManipulaTabelas(visual,null);
        
        int tamanhoVagas = manip.getTamanhoTabelaVagas();
            
            ArrayList<Vaga> listaVagas = new ArrayList();
                for(int x=0;x<tamanhoVagas;x++){
                listaVagas.add(manip.getLinhaTabelaVagas(x)); 
                }          
    return listaVagas;
    }
    
    //Quando escolhida a opção Entrar, verifica se a vaga comporta o veiculo, caso esteja livre. Retorna 0 se estiver ocupada, 1 se comportar e 2 se não comportar.
    public static int verificaVaga(Veiculo veiculo,Vaga vaga){
        if(!vaga.isDisponivel()){
            return 0;
            }else
        {
            if(veiculo.getAltura() <= vaga.getLimiteAltura() && 
               veiculo.getComprimento() <= vaga.getLimiteComprimento() && 
                    veiculo.getLargura() <= vaga.getLimiteLargura() && 
                    veiculo.getPeso() <= vaga.getLimitePeso())
                    return 1;   
             else
                return 2;
        }
        
    }
    
    //Retorna a posição da vaga na lista, de acordo com seu ID.
    public int getPosicaoVaga(Vaga vaga){
        Relatorio relatorio = new Relatorio();
        relatorio.getInstance();
        
        int contadorVaga=0;
            int contador1=0;
                for(Vaga X: relatorio.getListaVagas()){
                    if(X.getID()== vaga.getID()){
                        vaga = X;
                        contadorVaga=contador1;
                        }
                    else
                        contador1++;
                }
        
        return contadorVaga;
    }
    
}
