## Financial Service

Financial Service nada mais é que o serviço responsável por realizar as operações requisitadas (transferência entre contas, depósito, saque e pagamento). Além disso, é possível realizar a consulta de todas as operações realizadas (aprovadas) de uma conta ou buscar por uma operação específica de uma conta.

Para executar o projeto, primeiro, deve ser iniciando o balance-service. Ele possui o setup inicial de algumas dependencias utilizadas nos outros projetos. Por exemplo: ele é o responsável por iniciar o container do RabbitMQ e as networks compartilhadas entre os serviços.

Assim, basta clonar o repositório [https://github.com/Felipecan/balance-service.git](https://github.com/Felipecan/balance-service.git) e executar o comando abaixo na pasta raiz:

```text
$ sudo docker-compose  up --build
```

O comando acima irá realizar o build da imagem e iniciar os containers do projeto balance-service.  

Em seguida, poderá ser feito o start de qualquer outro serviço. Para isso, basta clonar cada um dos projetos e realizar o mesmo comando acima na pasta raiz correspondente ao projeto.

## Funcionamento:

Ao realizar uma operação financeira qualquer, o financial-service vai até o account-service verificar se a conta em questão existe e em seguida, ele verifica se a mesma está ativa. 

Nos casos onde é realizado um crédito na conta, o serviço basicamente insere a operação em uma fila do RabbiMQ para que a mesma possa ser enviada assíncronamente para o balance-service atualizar o saldo da conta. 

Entretanto, nos casos onde é realizado um débito na conta, é necessário que seja calculado o saldo real da conta. Assim, o financial-service consulta por todas as operações que ainda não foram de fato executadas e faz uma estimativa do saldo, em seguida é comparado com o saldo atual da conta e não sendo negativo, a operação é inserida na fila para ser executada posteriormente.

![](./imgs/wallet.drawio.png)

## Endpoints:

1. /financial/operations/transfer/{fromAccountId}/to/{toAccountId}

   Endpoint responsável por realizar a transfência entre contas. fromAccountId se trata da conta de origem e toAccountId a conta de destino. Além disso, é necessário o envio do seguinte payload:
   
   
   ```java
   {
      "accountId": 1,
      "toAccountId": 2,
      "value": 100.0
   }
   ```
   
2. /financial/operations/withdraw/from/{accountId}

   Endpoint responsável por realizar o saque na conta. É necessário passar o accountId na URL, referente a conta que fará o saque. O payload abaixo também é requerido:
   
   ```java
   {
      "accountId": 1,
      "value": 100.0
   }
   ```   
   
3. /financial/operations/deposit/in/{accountId}

   Endpoint responsável por realizar o depósito na conta. É necessário passar o accountId na URL, referente a conta que fará e receberá o depósito. O payload abaixo também é requerido:
   
   ```java
   {
      "accountId": 1,
      "value": 100.0
   }
   ```  
   
4. /financial/operations/payment

   Endpoint responsável por realizar o pagamento de contas. O payload abaixo é requerido:
   
   ```java
   {
      "accountId": 1,
      "value": 100.0,
      "billId": 1
   }
   ```  
   
5. /financial/operations/{id}/account/{accoutId}

	Endpoint responsável por buscar uma operação específica para determinada conta. O campo id na URL se trata do id da operação e accountId o id da conta que a realizou.


6. /financial/operations/from/{accountId}

	Endpoint responsável por trazer todo histórico de movimentações financeiras realizada pela conta. O parâmetro accountId na URL se trata da conta que será consultada para realizar a busca do histórico.
   

#### Links:

1. [balance-service](https://github.com/Felipecan/balance-service.git)

2. [account-service](https://github.com/Felipecan/account-service.git)