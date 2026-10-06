package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.HashMap;
import java.util.Map;

public class Item implements Prototype<Item> {
    private final String itemId;
    private int durability;
    private Map<String, Double> buffs;

    public Item(String itemId, int durability, Map<String, Double> buffs) {
        this.itemId = itemId;
        this.durability = durability;
        this.buffs = (buffs != null) ? new HashMap<>(buffs) : new HashMap<>();
    }

    public Item(Item source, boolean deepCopy) {
        this.itemId = source.itemId;
        this.durability = source.durability;
        if (deepCopy) {
            this.buffs = (source.buffs != null) ? new HashMap<>(source.buffs) : new HashMap<>();
        } else {
            this.buffs = source.buffs;
        }
    }

    public String getItemId() { return itemId; }
    public int getDurability() { return durability; }
    public void setDurability(int durability) { this.durability = durability; }
    public Map<String, Double> getBuffs() { return buffs; }
    public void setBuffs(Map<String, Double> buffs) { this.buffs = buffs; }

    public void addBuff(String k, Double v) {
        if (this.buffs == null) this.buffs = new HashMap<>();
        this.buffs.put(k, v);
    }

    @Override
    public Item shallowCopy() {
        return new Item(this, false);
    }

    @Override
    public Item deepCopy() {
        return new Item(this, true);
    }
}