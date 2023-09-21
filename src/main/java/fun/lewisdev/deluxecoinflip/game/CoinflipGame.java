/*
 * DeluxeCoinflip Plugin
 * Copyright (c) 2021 - 2022 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.deluxecoinflip.game;

import fun.lewisdev.deluxecoinflip.DeluxeCoinflipPlugin;
import fun.lewisdev.deluxecoinflip.menu.Base64Util;
import fun.lewisdev.deluxecoinflip.utility.ItemStackBuilder;
import fun.lewisdev.deluxecoinflip.utility.universal.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CoinflipGame {

    private final UUID uuid;
    private OfflinePlayer player;
    private String provider;
    private long amount;
    private ItemStack cachedHead;

    public CoinflipGame(UUID uuid, String provider, long amount) {
        this.uuid = uuid;
        this.provider = provider;
        this.amount = amount;
        this.cachedHead = XMaterial.PLAYER_HEAD.parseItem();
        Bukkit.getScheduler().runTaskAsynchronously(JavaPlugin.getProvidingPlugin(DeluxeCoinflipPlugin.class), () -> {
            this.player = Bukkit.getOfflinePlayer(uuid);
            if(Bukkit.getOnlineMode()) {
                this.cachedHead =  Base64Util.getSkull(player.getUniqueId());
            }else{
                this.cachedHead = new ItemStackBuilder(XMaterial.PLAYER_HEAD.parseItem()).setSkullOwner(player).build();
            }
        });
    }

    public CoinflipGame(UUID uuid, String provider, long amount, OfflinePlayer player, ItemStack cachedHead) {
        this.uuid = uuid;
        this.provider = provider;
        this.amount = amount;
        this.cachedHead = cachedHead;
        this.player = player;
    }

    public UUID getPlayerUUID() {
        return uuid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
        if(this.amount < 0) this.amount = 0;
    }

    public OfflinePlayer getOfflinePlayer() {
        return player;
    }

    public ItemStack getCachedHead() {
        return cachedHead.clone();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public CoinflipGame clone() {
        return new CoinflipGame(uuid, provider, amount, player, cachedHead);
    }

}
