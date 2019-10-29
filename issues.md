# ISSUES
---
### ESCOPO
- [x] Especificar tarefas e escopo

---
### CONFIGURACOES
- [x] **Configurações Spring**
  - [x] dependencias POM.xml
  - [x] classes Spring Framework 
    - [x] SpringInitConfig (substitui o WEB.xml)
    - [x] SpringJpaConfig
    - [x] SpringRootConfig

- [x] **Apache tomcat 7 (2.2)**
 - [x] dependencia POM.xml 
 
- [x] **MySQL** 
  - [x] dependencia POM.xml
  - [x] verificar versão
  
---
### DOMINIO 
- [x] **Classes de Domínio (Beans/Entidades)**
  - [x] CaixaAtm
  - [x] Conta
  - [x] MovimentoCaixa
  - [x] MovimentoConta
  - [x] StatusCaixa (Enum)
  - [x] TipoMovimento (Enum)
  
---
### DAO
- [x] **Classes DAO**
  - [x] Implementacoes
  - [x] Metodos necessarios para manipulacao das entidades
  
---
### SERVICO
- [x] **Classes de Servico**
  - [x] Implementacoes
  - [x] Funcao de distribuicao de notas no tipo de movimento SAQUE

---
### CONTROLER
- [x] **Classe responsavel por disponibilizar os servicos do Caixa ATM**
  - [x] Metodo cadastrar caixa ATM
  - [x] Abertura de caixa
  - [x] Cadastrar conta
  - [x] Cadastrar movimento de caixa
  - [x] Emitir extrato
  - [x] Fechar caixa (Relatorio de fechamento)
  
  
 ---
 ## EXTRAS
 - [ ] Docker 
 - [x] Processo de resolução do desafio: README-LUCAS.md
 - [x] Separation of Concerns
  
  
 ---
 ## PROBLEMAS
 - [ ] Problemas entre versão JDK e tomcat. Provavelmente seja entre dependencias do Maven.

 
