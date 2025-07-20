# 📄 Documento de Requisitos Funcionais – Versão 2.1
**Sistema:** Marketplace de Artesanato Personalizado  
**Versão:** 2.1  
**Data:** 17/07/2025  
**Autor:** [Seu Nome]

---

## 🧭 Visão Geral

Esta versão amplia a plataforma com funcionalidades de microSaaS voltadas para **gestão, engajamento e monetização**. A proposta é transformar o sistema em um hub completo para artesãos, permitindo:

- Captura e gestão de leads
- Organização de eventos online com certificação
- Venda de serviços e cursos
- Criação de clube de assinaturas VIP
- Acompanhamento por dashboards inteligentes

---

## 🧑‍💼 Perfis de Acesso

| Perfil     | Permissões novas na V2.1                                           |
|------------|---------------------------------------------------------------------|
| ADMIN      | Gestão global de eventos, leads, certificados, assinaturas         |
| ARTISTA    | Criar eventos, cursos, gerir leads e assinaturas                   |
| CUSTOMER   | Inscrever-se em eventos, cursos, assinar plano VIP                 |

---

## 🔧 Funcionalidades Adicionadas – V2.1

### 1. 📋 Gestão de Leads e CRM Leve

- [ ] Captura de lead via formulário (curso, produto ou evento)
- [ ] Listagem de contatos com filtros por interesse
- [ ] Criação manual de leads pelo artista
- [ ] Funil de status (ex: novo, contatado, convertido)
- [ ] Exportação dos leads (CSV)

---

### 2. 📅 Eventos Online com Certificação

- [ ] Criação de eventos: nome, descrição, data/hora, link de transmissão
- [ ] Página de inscrição para clientes
- [ ] Lista de participantes com presença
- [ ] Geração automática de certificados com nome e data
- [ ] Histórico de eventos realizados

---

### 3. 🛠️ Marketplace de Serviços

- [ ] Cadastro de serviços personalizados (ex: mentoria, consultoria)
- [ ] Escolha de agenda ou disponibilidade
- [ ] Agendamento por parte do cliente
- [ ] Checkout como produto/serviço avulso
- [ ] Feedback após entrega do serviço

---

### 4. 🎓 Cursos com Captura de Leads

- [ ] Cursos gratuitos e pagos
- [ ] Captura de email/nome mesmo para gratuitos (nutrição de lead)
- [ ] Acompanhamento de progresso
- [ ] Emissão de certificado de conclusão

---

### 5. 💳 Clube de Assinaturas

- [ ] Planos mensais com conteúdos exclusivos
- [ ] Produtos e cursos com preço diferenciado para assinantes
- [ ] Pagamento recorrente (manual inicialmente, integração futura)
- [ ] Gerenciamento de assinaturas no painel do cliente
- [ ] Conteúdo visível apenas para assinantes (aulas, moldes, ofertas)

---

### 6. 📈 Dashboard de Inteligência

- [ ] Visualização de:
    - Produtos mais vendidos
    - Aulas mais assistidas
    - Insumos com estoque crítico
    - Receita x despesa
    - Evolução de leads
- [ ] Filtros por período e tipo de dado
- [ ] Alertas automáticos no painel do artista

---

## 📊 Casos de Uso (adicionais)

| ID   | Caso de Uso                   | Atores         | Descrição                                    |
|------|-------------------------------|----------------|-----------------------------------------------|
| CU09 | Capturar lead em curso/evento| Cliente        | Preenche formulário com nome e email          |
| CU10 | Listar e gerenciar leads     | Artista        | Visualiza lista com filtros e exporta         |
| CU11 | Criar evento ao vivo         | Artista        | Cria evento com transmissão e inscrições      |
| CU12 | Emitir certificado de evento | Artista/Admin  | Gera certificado com nome e evento            |
| CU13 | Cadastrar novo serviço       | Artista        | Cria item do tipo “serviço” no painel         |
| CU14 | Assinar plano VIP            | Cliente        | Compra acesso recorrente para benefícios      |
| CU15 | Acessar dashboard de métricas| Artista        | Visualiza estatísticas do próprio conteúdo    |

---

## 🛑 Regras de Negócio

- Cursos gratuitos devem obrigar captura de email para acesso
- Apenas clientes que participaram de eventos podem receber certificados
- Serviços devem ser entregues dentro do prazo acordado
- Artistas só podem ver seus próprios leads e métricas
- Assinaturas ativas liberam acesso a conteúdos premium

---

## 📆 Roadmap de Implementação

| Sprint | Tema                         | Funcionalidades principais                  |
|--------|------------------------------|---------------------------------------------|
| 1      | Leads + CRM Leve             | Captura e gerenciamento de contatos         |
| 2      | Eventos com certificado      | Criação, inscrição e emissão de certificados|
| 3      | Serviços e agendamentos      | Catálogo de serviços e marcação de horários |
| 4      | Clube de Assinatura          | Criação de planos e controle de acesso VIP  |
| 5      | Dashboards e alertas         | Métricas e painéis para o artista           |

---

## 🧠 Visão Estratégica

Esses novos módulos aumentam o **lifetime value** do cliente, promovem **engajamento recorrente** e transformam a plataforma em um verdadeiro **ecossistema digital** para artesãos.

---

## 🌐 Tecnologias Sugeridas (complementares)

- Armazenamento de certificados em PDF → AWS S3
- Geração de certificados → Java PDFBox ou iText
- Eventos → Link Jitsi ou Zoom com integração básica
- Leads → Banco + fila opcional (ex: RabbitMQ para futuras automações)
- Assinatura → Integração futura com Stripe ou Mercado Pago Recorrente

---
