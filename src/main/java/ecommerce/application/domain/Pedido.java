package ecommerce.application.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cod_pedido;
	@ManyToOne
	@JoinColumn(name="cod_cliente")
	private Cliente cliente;
	

	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	@OneToMany(mappedBy="pedido",cascade = CascadeType.ALL)
	private List<ItemPedido> itempedido=new ArrayList<>();
	

	@JsonFormat(pattern="dd/MM/yyyy HH:mm",timezone = "GMT-03:00")
	private Date date_pedido;
	
	public Pedido() {
		
	}

	public Integer getCod_pedido() {
		return cod_pedido;
	}
	
	public double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : itempedido) {
			soma=soma+ip.getSubTotal();
		}
		return soma;
	}

	public void setCod_pedido(Integer cod_pedido) {
		this.cod_pedido = cod_pedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDate_pedido() {
		return date_pedido;
	}

	public void setDate_pedido(Date date_pedido) {
		this.date_pedido = date_pedido;
	}

	public Pedido(Cliente cliente, Date date_pedido) {
		super();
		this.cliente = cliente;
		this.date_pedido = date_pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod_pedido == null) ? 0 : cod_pedido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (cod_pedido == null) {
			if (other.cod_pedido != null)
				return false;
		} else if (!cod_pedido.equals(other.cod_pedido))
			return false;
		return true;
	}

	public List<ItemPedido> getItempedido() {
		return itempedido;
	}

	public void setItempedido(List<ItemPedido> itempedido) {
		this.itempedido = itempedido;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getCod_pedido());
		builder.append(", Instante: ");
		builder.append(sdf.format(getDate_pedido()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situação do pagamento: ");
		builder.append(getPagamento().getDescricaostatus());
		builder.append("\nDetalhes:\n");
		for (ItemPedido ip : getItempedido()) {
			builder.append(ip.toString());
		}
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}
	

}
