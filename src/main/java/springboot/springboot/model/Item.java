package springboot.springboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "items")
@ApiModel(description = "All details about the Item. ")
public class Item {

    @ApiModelProperty(notes = "The database generated item no")
    private long itemNo;

    @ApiModelProperty(notes = "The item name")
    private String name;

    @ApiModelProperty(notes = "The item amount")
    private int amount;

    @ApiModelProperty(notes = "The item inventory code")
    private long inventoryCode;

    public Item() {
    }

    public Item(long itemNo, String name, int amount, long inventoryCode) {
        this.itemNo = itemNo;
        this.name = name;
        this.amount = amount;
        this.inventoryCode = inventoryCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getItemNo() {
        return itemNo;
    }

    public void setItemNo(long itemNo) {
        this.itemNo = itemNo;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "inventory_code", nullable = false)
    public long getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(long inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemNo=" + itemNo +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", inventoryCode=" + inventoryCode +
                '}';
    }
}
