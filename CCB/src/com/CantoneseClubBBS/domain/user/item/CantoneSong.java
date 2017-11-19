/**
 * 
 */
package com.CantoneseClubBBS.domain.user.item;

/**
 * 粤语歌道具，使用后可以得到若干论坛货币
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月25日
 * @version 1.0
 * @update_date
 */
public class CantoneSong {

	private int get_max_money = 140;
	private int get_min_money = 1;

	private int id;
	/** 物品的数量 */
	private int count;
	/** 物品的描述 */
	private String Description = "在大街上，突然想唱粤语歌，引起了路人的注意，" + "可以得到" + get_max_money + "~" + get_min_money + "CCB";
	/** 物品的价格（卖给商店） */
	private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getGet_max_money() {
		return get_max_money;
	}

	public void setGet_max_money(int get_max_money) {
		this.get_max_money = get_max_money;
	}

	public int getGet_min_money() {
		return get_min_money;
	}

	public void setGet_min_money(int get_min_money) {
		this.get_min_money = get_min_money;
	}

	@Override
	public String toString() {
		return "CantoneSong [get_max_money=" + get_max_money + ", get_min_money=" + get_min_money + ", id=" + id
				+ ", count=" + count + ", Description=" + Description + ", price=" + price + "]";
	}

}
