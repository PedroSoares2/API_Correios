API de fácil instalação para buscar qualquer cep Brasileiro

Conceito
-Basicamente, ao subir o container, ela baixa esse csv com 900k de endereços e salva na tabela 'correio.address' dentro do MySQL.
Depois do setup é possível pesquisar de forma fácil, via API REST, qualquer cep Brasileiro.

Stack
-> Java 11+
-> MySQL
-> Spring Family

Para compilar e testar
- buildar e subir a API, ela pode demorar um tempo considerável para baixar todos os CEPs e inserir no MySQL.
Enquanto esse setup não termina, você vai receber o erro 503, conforme exemplo abaixo.

200 OK
{
    "zipcode": "03358150",
    "street": "Rua Ituri",
    "district": "Vila Formosa",
    "state": "SP",
    "city": "São Paulo"
}

503- No service Unavailable
{
    "timestamp": "2023-01-16T23:27:34.962+00:00",
    "status": 503,
    "error": "Service Unavailable",
    "message": "This service is being installed, please wait a few moments.",
    "path": "/zip/03358150"
}
