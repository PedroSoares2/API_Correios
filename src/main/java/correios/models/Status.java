package correios.models;

public enum Status {
	NEED_SETUP,  // Precisa baixar o CSV dos correios
	SETUP_RUNNING,  // Está baixando/salvando no banco
	READY;  // Servico está apto para ser consumido
}
