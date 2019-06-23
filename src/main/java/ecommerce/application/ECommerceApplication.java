package ecommerce.application;


import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ecommerce.application.domain.Categoria;
import ecommerce.application.domain.Cliente;
import ecommerce.application.domain.Endereco;
import ecommerce.application.domain.ItemPedido;
import ecommerce.application.domain.Pagamento;
import ecommerce.application.domain.Pedido;
import ecommerce.application.domain.Produto;
import ecommerce.application.enums.Perfil;
import ecommerce.application.enums.TipoPagamento;
import ecommerce.application.repositories.CategoriaRepository;
import ecommerce.application.repositories.ClienteRepository;
import ecommerce.application.repositories.EnderecoRepository;
import ecommerce.application.repositories.ItemPedidoRepository;
import ecommerce.application.repositories.PagamentoRepository;
import ecommerce.application.repositories.PedidoRepository;
import ecommerce.application.repositories.ProdutoRepository;
import ecommerce.application.services.EmailService;
import ecommerce.application.services.SmtpEmailService;

@SpringBootApplication
public class ECommerceApplication implements CommandLineRunner{
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@Autowired
	private ItemPedidoRepository itempedidoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		

		
		//Populando com categorias
		Categoria cat1 = new Categoria("Informatica");
		Categoria cat2 = new Categoria("Categoria2");
		Categoria cat3 = new Categoria("Categoria3");
		Categoria cat4 = new Categoria("Categoria4");
		Categoria cat5 = new Categoria("Categoria5");
		Categoria cat6 = new Categoria("Categoria6");
		
		//Populando com produtos
		Produto p1 = new Produto("Computador",500.0);
		Produto p2 = new Produto("Celular",200.0);
		
		//Flow
		cat1.addProduto(p1);
		cat1.addProduto(p2);
		p1.setCategoria(cat1);
		p2.setCategoria(cat1);
		
		Cliente c1 = new Cliente("Lucas MAchado","lucasvufma@gmail.com","60908095351",encoder.encode("123"));;
		Cliente c2 = new Cliente("Lucas V. P. MAchado","lucasvufma124123@gmail.com","60908095350",encoder.encode("123456"));;
		c2.addPerfil(Perfil.ADMIN);
		Endereco e1 = new Endereco("APartamento 201","6","Logradouro","Calhau","65073143", "São Luís","Ma",c1);
		Endereco e2 = new Endereco("Cookie complemento","66","Logradouro","Calhau","65073143", "São Luís","Ma",c2);
		c1.setEndereco(e1);
		c2.setEndereco(e2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(c1,sdf.parse("30/09/2017 10:32"));
		
		Pagamento pagto1 = new Pagamento(TipoPagamento.Boleto,0,ped1);
		
		ItemPedido item1 = new ItemPedido(1,0.0, ped1, p1);
		
		ped1.getItempedido().addAll(Arrays.asList(item1));
	
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6));
		produtoRepository.saveAll(Arrays.asList(p1,p2));
		clienteRepository.saveAll(Arrays.asList(c1,c2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		pedidoRepository.saveAll(Arrays.asList(ped1));
		pagamentoRepository.saveAll(Arrays.asList(pagto1));
		itempedidoRepository.saveAll(Arrays.asList(item1));

		
	}

}
