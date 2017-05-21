<h1> ADVENTURE 5 </h1>

<h2> Nuno Tomás (81718), Rita Ferreira (69836), David Anil (79710)\\ Diogo Freitas (81586), Alcino Albuquerque (78753) </h2>

<h3> Teste 1: 100 Reads </h3>

Neste teste de carga foi necessário fazer dois tipos de HTTP Request diferentes, um POST e em seguida um GET para cada correspondente módulo. O POST tem como objectivo a introdução dos dados, para ser possivel a leitura destes posteriormente com o metodo GET. 
Após diversas simulações, com o Jmeter, os resultados que se seguem demonstram a capacidade de interacção que a FenixFramework tem nos serviços remotos, neste caso a nossa aplicação Adventures.

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms)
------------ | -------------  | -------------  | ------------- 
2000 | 78.3/sec | 2 | 5897 
1300 | 65.4/sec | 1.8 | 6195
800 | 78.3/sec | 1.7 | 1857
