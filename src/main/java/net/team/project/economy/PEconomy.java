package net.team.project.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.team.project.Project;
import net.team.project.utils.User;
import net.team.project.utils.UserH;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class PEconomy implements Economy {

    private final String balanceLocation = "balance";

    /* In use */

    @Override
    public boolean isEnabled() {
        return true;
    }

        /* Balance */

    @Override
    public double getBalance(String p) {
        return Bukkit.getPlayer(p).hasPlayedBefore()
                ? UserH.userList.get(Bukkit.getPlayer(p).getUniqueId()).getConfig().getDouble(balanceLocation)
                : 0.0d;
    }

    @Override
    public double getBalance(OfflinePlayer p) {
        User user = new User(Project.getInstance(), p.getUniqueId());
        return p.hasPlayedBefore()
                ? user.getConfig().getDouble(balanceLocation)
                : 0.0d;
    }

    @Override
    public double getBalance(String p, String arg1) { // player name + world
        return getBalance(p);
    }

    @Override
    public double getBalance(OfflinePlayer p, String arg1) { // offline player + world
        return getBalance(p);
    }

    public void setBalance(String p, double a) {
        UserH.userList.get(Bukkit.getPlayer(p).getUniqueId()).getConfig().set(balanceLocation, a);
    }

    public void setBalance(OfflinePlayer p, double a) {
        User user = new User(Project.getInstance(), p.getUniqueId());
        user.getConfig().set(balanceLocation, a);
        user.save();
    }

        /* Deposit on player account */

    @Override
    public EconomyResponse depositPlayer(String p, double a) {
        double newBalance = getBalance(p)+a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, double a) {
        double newBalance = getBalance(p)+a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String p, String arg1, double a) { // world dont care
        double newBalance = getBalance(p)+a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, String arg1, double a) {
        double newBalance = getBalance(p)+a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

        /* Withdraw from player account */

    @Override
    public EconomyResponse withdrawPlayer(String p, double a) {
        double newBalance = getBalance(p)-a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, double a) {
        double newBalance = getBalance(p)-a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String p, String arg1, double a) {
        double newBalance = getBalance(p)-a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, String arg1, double a) {
        double newBalance = getBalance(p)-a;
        setBalance(p, newBalance);
        return new EconomyResponse(a, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

        /* Not in use */

    @Override
    public EconomyResponse bankBalance(String arg0) {

        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse bankHas(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, String arg1) {

        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {

        return null;
    }

    @Override
    public boolean createPlayerAccount(String arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(String arg0, String arg1) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {

        return false;
    }

    @Override
    public String currencyNamePlural() {

        return null;
    }

    @Override
    public String currencyNameSingular() {

        return null;
    }

    @Override
    public EconomyResponse deleteBank(String arg0) {

        return null;
    }

    @Override
    public String format(double arg0) {

        return null;
    }

    @Override
    public int fractionalDigits() {

        return 0;
    }

    @Override
    public List<String> getBanks() {

        return null;
    }

    @Override
    public String getName() {

        return null;
    }

    @Override
    public boolean has(String arg0, double arg1) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer arg0, double arg1) {
        return false;
    }

    @Override
    public boolean has(String arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean hasAccount(String arg0) {

        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0) {

        return true;
    }

    @Override
    public boolean hasAccount(String arg0, String arg1) {

        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0, String arg1) {

        return true;
    }

    @Override
    public boolean hasBankSupport() {

        return false;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, String arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
        return null;
    }
}
