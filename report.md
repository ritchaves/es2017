<h1> ADVENTURE 5 </h1>

<h3> Nuno Tomás (81718), Rita Ferreira (69836), David Anil (79710), Diogo Freitas (81586), Alcino Albuquerque (78753) </h3>

<h4> Teste 1: 100 Reads </h4>

Neste teste de carga foi necessário fazer dois tipos de HTTP Request diferentes, um POST e em seguida um GET para cada correspondente módulo. O POST tem como objectivo a introdução dos dados, para ser possivel a leitura destes posteriormente com o metodo GET. 
Após diversas simulações, com o Jmeter, os resultados que se seguem demonstram a capacidade de interacção que a FenixFramework tem nos serviços remotos, neste caso a nossa aplicação Adventures.

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms)
------------ | -------------  | -------------  | ------------- 
2000 | 78.3/sec | 2 | 5897 
1300 | 65.4/sec | 1.8 | 6195
800 | 78.3/sec | 1.7 | 1857

Nenhum dos presentes testes foi efectuado com loops, apenas manipulação do número de threads. Os tempos de resposta máxima vão variar bastante entre eles, pelo que a média entre estes poderá ser uma estimativa incorrecta da realidade, pois esta vai depender bastante do módulo ao qual as threads estão a fazer a leitura. No entanto, reparou-se um tempo maior de resposta nos casos do Broker e do Adventure. \\
De acordo, com os resultados observados é possível concluir-se que quanto maior for o número de utilizadores, menor o tempo de resposta máximo e maior será o throughput (número de requests que o servidor consegue lidar). Isto é justificado pela política optimista de leitura da FenixFramework.


<h4> Teste 2: 30 Writes </h4>

<h4> Teste 3: 100 Writes </h4>

Neste teste de carga foi necessário fazer dois tipos de HTTP Request diferentes, um POST e em seguida um GET para cada correspondente módulo. O POST tem como objectivo a introdução dos dados, para ser possivel a leitura destes posteriormente com o metodo GET. 
Após diversas simulações, com o Jmeter, os resultados que se seguem demonstram a capacidade de interacção que a FenixFramework tem nos serviços remotos, neste caso a nossa aplicação Adventures.

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms)
------------ | -------------  | -------------  | ------------- 
  100 | 17.3/sec | 22 | 29408
  50 | 57.3/sec | 12 | 3259
  25 | 63.1/sec | 10 | 859 
