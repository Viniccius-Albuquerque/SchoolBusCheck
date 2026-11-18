# SchoolBusCheck

Sistema de terminal para registrar a entrada de alunos em um ônibus escolar e consultar os registros por data.

## Requisitos
- Java 17 ou superior
- Maven instalado

## Executar
Na pasta do projeto:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="br.com.schoolbuscheck.Main"
```

Se o comando `exec:java` não estiver disponível, compile e execute pela sua IDE (classe `br.com.schoolbuscheck.Main`) ou adicione o plugin Maven Exec.

## Testes
```bash
mvn test
```

## Como funciona
- O aluno informa RA e nome.
- O sistema registra data e horário automaticamente.
- O mesmo RA só pode ter uma entrada por dia.
- O administrador consulta as datas disponíveis e os alunos registrados em cada dia.
- Os dados são persistidos em `dados/registros.json`.

## Acesso administrativo
Nesta versão acadêmica, a opção administrativa está disponível pelo menu inicial e não possui senha. Em um cenário real, deve ser adicionada autenticação.

## Organização
- `model`: entidades do domínio.
- `repository`: leitura e gravação do JSON.
- `service`: regras de negócio.
- `factory`: criação de registros.
- `observer`: notificação após uma entrada.
- `facade`: ponto de acesso simplificado aos serviços.
- `ui`: menus e interação com o terminal.

## Padrões e princípios demonstrados
- **Factory (criacional):** `CheckInFactory` centraliza a criação de registros.
- **Facade (estrutural):** `SchoolBusFacade` oferece uma interface simples para a aplicação.
- **Observer (comportamental):** `CheckInNotifier` notifica ouvintes após um registro.
- **SOLID:** separação de responsabilidades; serviço depende da interface `CheckInRepository`; novas implementações de repositório podem ser adicionadas sem alterar as regras.
- **GRASP:** `CheckInService` atua como controlador do caso de uso; `JsonCheckInRepository` é especialista na persistência e consulta dos registros.

> Observação: o projeto mantém o escopo funcional simples. Os padrões foram aplicados na organização interna, sem acrescentar funcionalidades desnecessárias.
