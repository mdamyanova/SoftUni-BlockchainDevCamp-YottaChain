package com.yottachain.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class Helpers {

    public static String html =
            "<h1>YottaChain - Simple Blockchain Network</h1><ul><li>GET <a href=\"/\">/</a></li><li>GET <a href=\"/info\">/info</a></li><li>GET <a href=\"/debug\">/debug</a></li><li>GET <a href=\"/debug/reset-chain\">/debug/reset-chain</a></li><li>GET <a href=\"/blocks\">/blocks</a></li><li>GET <a href=\"/blocks/{index}\">/blocks/{index}</a></li><li>GET <a href=\"/transactions/pending\">/transactions/pending</a></li><li>GET <a href=\"/transactions/confirmed\">/transactions/confirmed</a></li><li>GET <a href=\"/transactions/{transactionHash}\">/transactions/{transationHash}</a></li><li>GET <a href=\"/balances\">/balances</a></li><li>GET <a href=\"/address/{address}/transactions\">/address/{address}/transactions</a></li><li>GET <a href=\"/address/{address}/balance\">/address/{address}/balance</a></li><li>POST <a href=\"/transactions/send\">/transactions/send</a></li><li>GET <a href=\"/peers\">/peers</a></li><li>POST <a href=\"/peers/connect\">/peers/connect</a></li><li>POST <a href=\"/peers/notify-new-block\">/peers/notify-new-block</a></li><li>GET <a href=\"/mining/get-mining-job/{address}\">/mining/get-mining-job/{address}</a></li><li>POST <a href=\"/mining/submit-mined-block\">/mining/submit-mined-block</a></li></ul>";

    public static String toISO8601UTC(ZonedDateTime date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }
}