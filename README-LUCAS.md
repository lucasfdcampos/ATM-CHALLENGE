## Processo de resolução do desafio
Para iniciar o desafio verifiquei algumas dependências necessárias gerenciáveis pelo Maven e configurações do Spring JPA. Após esta etapa de configuração iniciei a fase de análise de requisitos, abstraindo as classes de domínio necessárias para o projeto.

### **Classes de domínio** ###

Identifiquei as classes de domínio (beans/entidades) que seriam necessárias para representar as abstrações do mundo real proposto pelo desafio. São elas: 
- **CaixaAtm**: representa o modelo de dados do Caixa ATM no banco.
- **Conta**: representa o modelo de dados da Conta do cliente.
- **MovimentoConta**: representa o modelo de dados de cada movimento de conta.
- **MovimentoCaixa**: representa o modelo de dados de cada movimento do Caixa ATM no banco.

### **Classes de persistência** ###

Como proposta de desenvolvimento, utilizei o padrão de projeto com classes DAO (Data Access Object) e trabalhei com as instruções baseadas em JPA com injeção de dependências a partir de interfaces DAO. São elas:
- **CaixaAtmDao** e sua implementação na classe: **CaixaAtmDaoImpl**.
- **ContaDao** e sua implementação na classe: **ContaDaoImpl**.
- **MovimentoContaDao** e sua implementação na classe: **MovimentoContaDaoImpl**.
- **MovimentoCaixaDao** e sua implementação na classe: **MovimentoCaixaDaoImpl**.

### **Camada de serviço (regras de negócios)** ###

Camada para separar a parte que não pertence à camada de persistência de dados, como citado acima. Deixando somente métodos referentes ao negócio.
O propósito de uma camada de serviço (Service Layer) tem justamente a responsabilidade dessa separação de camadas, mantendo o projeto mais organizado. As classes que implementam essas interfaces contém algumas anotações que marca a classe como bean gerenciável ao Spring do tipo “serviço”.
 Interfaces e classes da camada de serviço:
- **CaixaAtmService** e sua implementação na classe: **CaixaAtmServiceImpl**.
- **ContaService** e sua implementação na classe: **ContaServiceImpl**.
- **MovimentoContaService** e sua implementação na classe: **MovimentoContaServiceImpl**.
- **MovimentoCaixaService** e sua implementação na classe: **MovimentoCaixaServiceImpl**.

### **Classe Rest** ###

A Classe BancoRestController é responsável por receber e responder as solicitações REST, como método POST do protocolo HTTP, por exemplo.
Nesta classe contém os métodos criados para atender os tópicos do desafio.


### **Cadastro de Caixa ATM** ###

Método para cadastro do Caixa ATM. Necessário envio do objeto CaixaAtm.

**Método: cadastrarCaixaAtm**
- POST url: localhost:8080/rest/atm/cadastroAtm
- { "numeroAtm" : "00001", "nomeAtm" : "Nome ATM 1", "serialAtm" : "Serial ATM 1" }


### **Abertura de Caixa ATM** ###

Método para abertura do Caixa ATM. Necessário o envio do objeto CaixaAtm. 
Neste método, como também no método citado acima o status do caixa é setado como ABERTO. 

Necessário o envio do Id no objeto. Há excessões para verificar se o caixa é válido e existente.

**Método: abrirCaixaAtm**
- POST url: localhost:8080/rest/atm/aberturaCaixaAtm
- { "id" : 1, "numeroAtm": "00001", "nome: " : "Nome ATM 1", "serialAtm" : "Serial ATM 1" }


### **Cadastro de conta do cliente** ###

Método para cadastro da conta do cliente.

**Método: cadastrarConta**
- POST localhost:8080/rest/atm/cadastroConta
- { "codigoAgencia" : "0001", "numeroConta" : "000001-1", "nome" : "Cliente 1", "saldo" : 3000 }
- { "codigoAgencia" : "0001", "numeroConta" : "000002-1", "nome" : "Cliente 2", "saldo" : 2000 }
- { "codigoAgencia" : "0001", "numeroConta" : "000003-1", "nome" : "Cliente 3", "saldo" : 6000 }


### **Movimento de caixa** ###

Método responsável por salvar movimento de caixa com operações de DEPOSITO, SAQUE e TRANSFERENCIA. O tipo de movimento é indicado conforme ordem do ENUM [TipoMovimento].

**Método: cadastrarMovimentoCaixa**
- POST localhost:8080/rest/atm/movimentoCaixa
// DEPOSITO - CHEQUE
- { "caixaAtm" : 1, "conta": 1, "tipoMovimento": 0, "valor": 200,00 }
// DEPOSITO - DINHEIRO
- { "caixaAtm" : 1, "conta": 1, "tipoMovimento": 1, "valor": 250,00 }
// SAQUE
- { "caixaAtm" : 1, "conta": 1, "tipoMovimento": 2, "valor": 100,00 }
// TRANSFERENCIA 
- { "caixaAtm" : 1, "conta": 1, "tipoMovimento": 3, "valor": 600,00, "contaDestino": 2 }

Neste método foram criados diversas exceções, como por exemplo, a validação se o caixa ATM existe e é válido, se está aberto, se a conta do cliente é válida e existe na base de dados. 
Para os tipos de movimento SAQUE e TRANSFERENCIA é verificado o saldo da conta do cliente, e para caso de SAQUE, verifica se o valor é maior que R$10,00 e o módulo do valor por 10 seja 0 (zero) para permitir a operação. Caso as informações para saque estejam corretas, através do método calcularCedulas é verificado quais serão as cédulas (por quantidade) conforme o valor da operação.

Para o tipo de movimento TRANSFERENCIA é verificado se a conta destino é valida e existe, após isso é lançado um novo movimento de conta e de caixa para registrar esta operação.
No final do processo são atualizados os dados da conta, movimento de conta e de caixa.


### **Extrato de conta** ###

Método responsável por retornar uma lista de objetos com os movimentos da conta informada. Neste método programei para buscar o extrato (movimento da conta) através do código de agência e numero de conta.
**Método: emitirExtrato**

- POST localhost:8080/rest/atm/emitirExtrato
- { "codigoAgencia" : "0001", "numeroConta" : "0000001-1" }


### **Fechamento de caixa** ###

Método responsável pelo fechamento do caixa ATM e retornar uma lista de objetos com os movimentos de caixa através do caixa ATM informado. Neste método é setado o status do caixa como FECHADO.
**Método: fecharCaixa**

- POST localhost:8080/rest/atm/fechamentoCaixa
- { "id" : 1, "numeroAtm": "00001", "nome: " : "Nome ATM 1", "serialAtm" : "Serial ATM 1" }
