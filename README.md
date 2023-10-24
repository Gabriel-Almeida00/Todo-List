# ToDo List

Este é um projeto simples de uma aplicação de gerenciamento de tarefas (ToDo List) em Java. Ele permite que o usuário crie, liste e atualize tarefas com base em categorias, prioridades e status. Além disso, as tarefas são salvas em um JSON para garantir a persistência dos dados.

## Requisitos

- Java 8 ou superior
- Gradle

## Funcionalidades

- Adicionar uma nova tarefa com os seguintes parâmetros:
  - Nome
  - Descrição
  - Data de término
  - Nível de prioridade (1-5)
  - Categoria
  - Status (ToDo, Doing e Done)

- Listar tarefas por:
  - Categoria
  - Prioridade
  - Status

- Atualizar uma tarefa existente

- Contar o número de tarefas concluídas, para fazer e em andamento
- ###  Alarmes
O novo recurso de alarme permite ao usuário habilitar um aviso para uma tarefa específica. Ao criar uma tarefa, o usuário pode definir o período de antecedência do alarme em minutos. O sistema verificará periodicamente se há tarefas com alarmes configurados próximos ou iguais ao momento atual e exibirá um alerta para o usuário.

- ###  Design Pattern e MVC
  O Projeto segue as boas práticas com clean code SOLID e atualmente estou usando modelo MVC, para a funcionalidade de Alarmes foi implementada  o padrão observer

## Como executar o projeto

1. Clone o repositório para sua máquina:

git clone https://github.com/seu-usuario/todo-list-java.git


2. Acesse o diretório do projeto:

cd todo-list-java


3. Compile os arquivos Java:

javac -d out src/*.java



4. Execute a classe Main:

java -classpath out Main


## Exemplo de Uso

1. Ao executar o programa, você verá um menu de opções.
2. Escolha a opção "Adicionar Tarefa" e forneça as informações da tarefa quando solicitado.
3. A tarefa será adicionada à lista e salva no arquivo "list.json".
4. Você pode listar as tarefas por categoria, prioridade ou status escolhendo as opções apropriadas no menu.
5. Para atualizar uma tarefa, escolha a opção "Atualizar Tarefa" no menu e siga as instruções.

## Persistência dos Dados

As tarefas são salvas em um arquivo txt chamado "list.json" no mesmo diretório onde o programa é executado. O arquivo é criado automaticamente se ainda não existir. Isso garante que as tarefas sejam persistidas entre as execuções do programa.

## Contribuição

Este projeto é apenas um exemplo simples, mas sinta-se à vontade para criar um fork, fazer melhorias e contribuir. Sinta-se livre para abrir problemas (issues) se encontrar algum bug ou tiver alguma sugestão.

