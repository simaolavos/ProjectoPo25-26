# BCI Application

## Estrutura do Repositório

- **Core:** `bci-core/`  
  Contém as classes de domínio principais da aplicação.

- **Interaction:** `bci-app/`  
  Contém as classes responsáveis pela interação com o utilizador.

- **UML diagrams:** `uml/`  
  Contém os diagramas UML da primeira entrega.

⚠️ **Importante:** Os nomes das classes já existentes **não devem ser alterados**.  
Algumas classes **não podem ser modificadas de forma alguma** — verifica os detalhes nas páginas do curso.

Nem todo o código precisa de estar completamente funcional em todas as entregas (podem aplicar-se penalizações). Consulta as condições de avaliação no site da disciplina.

---

## Compilação e Execução

A compilação de todo o projeto (incluindo a biblioteca **po-uilib**) é feita automaticamente através do `Makefile` principal.  
Basta executar o seguinte comando na raiz do projeto:

```bash
make
```

Configurar o CLASSPATH

Antes de executar o programa, é necessário configurar o CLASSPATH para incluir a biblioteca po-uilib.jar, usada para a interface textual.
```bash
Em Linux/MacOS:
export CLASSPATH=.:po-uilib/po-uilib.jar
```

```bash
Em Windows (PowerShell):
set CLASSPATH=.;po-uilib\po-uilib.jar
```


Estas instruções assumem que o ficheiro po-uilib.jar é gerado dentro da pasta po-uilib após a compilação.
Se estiver noutro local, ajusta o caminho em conformidade.

Podes confirmar se o CLASSPATH está corretamente configurado com:
```bash
echo $CLASSPATH
```


Se quiseres definir esta configuração permanentemente, adiciona o comando ao teu ficheiro de inicialização (.bashrc, .zshrc, etc.).
