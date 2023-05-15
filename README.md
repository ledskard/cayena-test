## Cayena backend teste técnico

## Instruções para usar o banco de dados:

Para utilizar o banco de dados, é necessário ter o Docker instalado na máquina. Em seguida, execute o seguinte comando para subir o container do MySQL:

```docker run -p 3306:3306 --name mysql-cayena -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cayenadb -e MYSQL_USER=luiz -e MYSQL_PASSWORD=root mysql:latest```

Para conectar-se ao banco de dados, execute o comando abaixo e digite a senha 'root' quando solicitada:

```docker exec -it mysql-cayena mysql -uluiz -p```

Uma vez conectado, execute o comando abaixo para selecionar o banco de dados "cayenadb":

```use cayenadb;```

Por fim, execute o comando abaixo para inserir dados na tabela de "supplier":

```
INSERT INTO supplier (id, created_at, name, updated_at) 
VALUES (1, NOW(), 'supplier 1', NOW()), 
       (2, NOW(), 'supplier 2', NOW()), 
       (3, NOW(), 'supplier 3', NOW());
 ```

# Instruções para rodar a aplicação e chamar os endpoints

Para rodar a aplicação é necessário o JAVA 11 instalado na sua máquina.

Após subir o banco de dados utilizando os comandos descritos acima, é necessário iniciar a aplicação para utilizar os endpoints da API.

Existe uma Postman Collection que pode ser encontrada dentro da pasta raiz do projeto.

Obrigado! 
