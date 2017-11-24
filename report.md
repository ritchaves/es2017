<h1> ADVENTURE 5 </h1>

<h3> Nuno Tomás (81718), Rita Ferreira (69836), David Anil (79710), Diogo Freitas (81586), Alcino Albuquerque (78753) </h3>

<h4> Teste 1: 100 Reads </h4>

Neste teste de carga foi necessário fazer dois tipos de HTTP Request diferentes, um POST e em seguida um GET para cada correspondente módulo. O POST tem como objectivo a introdução dos dados, para ser possivel a leitura destes posteriormente com o metodo GET. <p>
Após diversas simulações, com o Jmeter, os resultados que se seguem demonstram a capacidade de interacção que a FenixFramework tem nos serviços remotos, neste caso a nossa aplicação Adventures.

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms)
------------ | -------------  | -------------  | ------------- 
2000 | 78.3/sec | 2 | 5897 
1300 | 65.4/sec | 1.8 | 6195
800 | 78.3/sec | 1.7 | 1857

Nenhum dos presentes testes foi efectuado com loops, apenas manipulação do número de threads. Os tempos de resposta máxima vão variar bastante entre eles, pelo que a média entre estes poderá ser uma estimativa incorrecta da realidade, pois esta vai depender bastante do módulo ao qual as threads estão a fazer a leitura. No entanto, reparou-se um tempo maior de resposta nos casos do Broker e do Adventure. <p>
De acordo, com os resultados observados é possível concluir-se que quanto maior for o número de utilizadores, menor o tempo de resposta máximo e maior será o throughput (número de requests que o servidor consegue lidar). Isto é justificado pela política optimista de leitura da FenixFramework.


<h4> Teste 2: 30 Writes </h4>
Este teste de carga, tal como no anterior, foram executados dois metódos de HTTP Request: o POST e o GET. Foi pedido também três "Process" ao nosso Adventures, entercalados entre os POST, de modo a verificar se os estados da aplicação eram alterados após terem sido criados os dados. <p>
A seguinte tabela mostra os resultados deste teste de carga:

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms) | Error (%)
------------ | -------------  | -------------  | ------------- | ------------- 
 2000| 24.2/sec|  2|  11940| 29.68
1300 |21.8/sec | 2| 8951| 44.64
800 |  24.2/sec| 2 | 3899| 38.23

Os resultados poderão apresentar falhas, pois em determinadas circunstâncias o Process Adventure falhava. Visto que os dados a serem manipulados no .txt, tem apenas um único input, existe a hipótese de ocorrerem cancelamentos da Aventura ou conflitos de escrita. <p>
Contudo é possível observar nestes resultados que existe um tempo maior de resposta quando o número de utilizadores aumenta, e uma diminuição do throughput. 

<h4> Teste 3: 100 Writes </h4>

Tal como nos testes anteriores, este teste vai ter dois tipos de HTTP Request diferentes, um POST e em seguida um GET para cada correspondente módulo. Sendo necessário posteriormente um loop de cinco Process Adventures. <p>
Após diversas simulações, com o Jmeter, podemos observar os resultados na seguinte tabela:

Nº Utilizadores | Avg. Throughput | Avg. Min response (ms) | Avg. Max response (ms)
------------ | -------------  | -------------  | ------------- 
  100 | 17.3/sec | 22 | 29408
  50 | 57.3/sec | 12 | 3259
  25 | 63.1/sec | 10 | 859 
  
Nenhum dos presentes testes foi efectuado com loops, apenas manipulação do número de threads.<p>
De acordo, com os resultados observados é possível concluir-se que quanto maior for o número de utilizadores, menor será o tempo de resposta máximo e menor será o throughput. Isto é justificado pela política optimista de leitura da FenixFramework. Pelo que quando são efectuadas operações de escrita a partir da Framework, vai existir um decrescimento de número de request a serem lidados. 

<h4> Conclusões Gerais </h4>

Tanto o primeiro teste (100 Reads) como o segundo (30 writes) comportam-se de maneira diferente do espectável. No entanto, nestes dois, o resultado da execução com um numero de utilizadores a 800 e a 2000 têm resultados semelhantes, ao que, com 1300 utilizadores os resultados pioram significativamente. Parece-nos, portanto, que a FenixFramework tem uma espécie de algoritmo inteligente onde prioritariza os reads/writes(se com 800 estaria a 98/2, com 2000 estaria 95/5, em caso do write ser os pedidos mais frequentes dos utilizadores), dependendo o teste e do número de utilizadores simultâneos.
