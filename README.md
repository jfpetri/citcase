Entregando Mercadorias - Case Técnico
======================

Um novo sistema de logística e sua ajuda é muito importante neste momento. Sua tarefa será desenvolver o novo sistema de entregas visando sempre o menor custo. Para popular sua base de dados o sistema precisa expor um Webservices que aceite o formato de malha logística (exemplo abaixo), nesta mesma requisição o requisitante deverá informar um nome para este mapa. É importante que os mapas sejam persistidos para evitar que a cada novo deploy todas as informações desapareçam. O formato de malha logística é bastante simples, cada linha mostra uma rota: ponto de origem, ponto de destino e distância entre os pontos em quilômetros.

- A B 10
- B D 15
- A C 20
- C D 30
- B E 50
- D E 30


Com os mapas carregados o requisitante irá procurar o menor valor de entrega e seu caminho, para isso ele passará o nome do ponto de origem, nome do ponto de destino, autonomia do caminhão (km/l) e o valor do litro do combustível, agora sua tarefa é criar este Webservices. Um exemplo de entrada seria, origem A, destino D, autonomia 10, valor do litro 2,50; a resposta seria a rota A B D com custo de 6,25.

## Arquitetura

Nesse projeto foi utilizado Spring Framework e para persistir os dados o [MongoDB Cloud](https://mongolab.com).

Para calcular a rota mais eficiente foi utilizado o [Algoritmo de Dijkstra](https://pt.wikipedia.org/wiki/Algoritmo_de_Dijkstra).


## Pré-Requisitos

- [Java Runtime Edition 7+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

- [Apache Tomcat 7+](http://tomcat.apache.org/)


## Instalação da aplicação

Executar no diretorio da aplicação
```
mvn clean install
```
## Serviços

#### POST /citcase/services/map/{mapName}

Cria um mapa no MongoDB ou  caso ja exista o mapa, atualiza o mesmo. 
A chave sera sempre o nome do mapa.

Formato: origem destino distancia

- A B 10
- B D 15
- A C 20
- C D 30
- B E 50
- D E 30


#### Resposta

- 201
	Mapa Criado com sucesso.

- 400
	Erro na requisição, parametro mal informado

- 500
	Erro ocorrendo algum processo interno

#### GET /citcase/services/route/{origem}/{destino}?performance={autonomia}&fuelPrice={precoCombistivel}&mapName={nomedomapa}

Calcula a rota, levando em conta a automonia e preço do combustível. É necessário ter previamente cadastrado o Mapa, e informá-lo no parametro <nomedomapa>


#### Resposta


- 200
	Solicitação executada com sucesso.
	Exemplo de retorno experado:
	
	```
		{"origin":"A","destination":"D","fuelPrice":2.5,"performance":10.0,"route":"A > B > D","distance":25.0,"totalCost":6.25}
	```

- 400
	Erro na requisição, parametro mal informado

- 500
	Erro ocorrendo algum processo interno



