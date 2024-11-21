# SolarWare

- Link do vercel: https://solarware-gs.vercel.app/
- Link do repositório: https://github.com/kauameloo/solarware-gs

SolarWare é uma aplicação web que demonstra o impacto e os benefícios da energia solar. O projeto inclui um simulador interativo para calcular o impacto ambiental e econômico da energia solar, informações sobre seus benefícios, e um formulário de contato para dúvidas ou sugestões.

## Tecnologias Utilizadas

- **Next.js** - Framework React para renderização do lado do servidor e construção de aplicações web otimizadas.
- **TypeScript** - Linguagem de programação que adiciona tipagem estática ao JavaScript, melhorando a segurança do código.
- **Tailwind CSS** - Framework CSS utilitário para estilização rápida e consistente.
- **shadcn/ui** - Biblioteca de componentes UI personalizados com integração ao Tailwind CSS.
- **Recharts** - Biblioteca para criação de gráficos e visualizações de dados interativas.

---

## Configuração do Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/solarware.git
   cd solarware
   ```

2. **Instale as dependências:**
   Certifique-se de que você tenha o Node.js instalado.

```bash
npm install
```

Inicie o servidor de desenvolvimento:

```bash
npm run dev
```

O projeto estará disponível em http://localhost:3000.

## Como Rodar a API em Java

1. **Navegue até o diretório da API:**

   Após clonar o repositório, entre na pasta da API:

   ```bash
   cd API-Java/DigitalMavericks
   ```

2. **Abra o projeto em uma IDE:**

   - Utilize sua IDE favorita, como **IntelliJ IDEA** ou **VS Code**.
   - Abra o diretório `API-Java/DigitalMavericks` como um projeto.

3. **Configure as dependências:**

   Certifique-se de que o Maven (ou outro gerenciador de dependências configurado no projeto) baixe todas as dependências automaticamente. Caso necessário, rode:

   ```bash
   mvn install
   ```

4. **Inicie a aplicação:**

   Navegue até a classe `Main` no projeto, localize o método `main` e inicie a aplicação diretamente pela IDE.

5. **Acesse a API:**

   Após iniciar, a API estará rodando localmente. Verifique o terminal para a porta padrão, geralmente `http://localhost:8080`.

## Funcionalidades

**Simulador de Impacto Solar**

Um simulador que permite calcular o impacto ambiental e econômico da energia solar, comparando-a com outras fontes de energia.

**Visualização de Dados com Recharts**

A aplicação utiliza gráficos interativos para exibir comparações de custos e emissões entre energia solar e fontes de energia tradicionais.

**Estudos de Caso**

Exibe exemplos reais de empresas que adotaram a energia solar e os benefícios obtidos, permitindo aos usuários visualizar o impacto positivo dessa tecnologia.

**Formulário de Contato**

Um formulário para que os usuários enviem dúvidas ou sugestões, facilitando o contato com a equipe.
