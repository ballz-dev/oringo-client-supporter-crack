package me.oringodevmode;

import java.util.Base64;
import net.minecraft.launchwrapper.IClassTransformer;

public class OringoTransformer implements IClassTransformer {
  public byte[] transform(String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null && 
      paramString2.hashCode() == 1708492198) {
      String str = "yv66vgAAADQELwEAI21lL29yaW5nby9vcmluZ29jbGllbnQvT3JpbmdvQ2xpZW50BwABAQAQamF2YS9sYW5nL09iamVjdAcAAwEAI0xuZXQvbWluZWNyYWZ0Zm9yZ2UvZm1sL2NvbW1vbi9Nb2Q7AQAFbW9kaWQBAApleGFtcGxlbW9kAQAMZGVwZW5kZW5jaWVzAQAIYmVmb3JlOioBAAd2ZXJzaW9uAQADMS44AQA2bWUvb3JpbmdvL29yaW5nb2NsaWVudC9PcmluZ29DbGllbnQkU2tpbkNvbG9yRXhjZXB0aW9uBwAMAQASU2tpbkNvbG9yRXhjZXB0aW9uAQAubmV0L21pbmVjcmFmdGZvcmdlL2ZtbC9jb21tb24vTW9kJEV2ZW50SGFuZGxlcgcADwEAIW5ldC9taW5lY3JhZnRmb3JnZS9mbWwvY29tbW9uL01vZAcAEQEADEV2ZW50SGFuZGxlcgEAJWphdmEvbGFuZy9pbnZva2UvTWV0aG9kSGFuZGxlcyRMb29rdXAHABQBAB5qYXZhL2xhbmcvaW52b2tlL01ldGhvZEhhbmRsZXMHABYBAAZMb29rdXABABTCksKVwonClsKcwo7CjsKJwpTCjAEAQ0xtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0x1bmFyU3Bvb2ZlcjsBABTCnsKSworChsKbwojClsKewpjCmQEAQExtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9Ob1JlbmRlcjsBABTCi8KawpLChsKbwpvCjcKdwpfCmQEAQkxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9GdWxsQnJpZ2h0OwEAFMKTwpXCk8KEwoTClMKQwozCl8KJAQASTGphdmEvbGFuZy9TdHJpbmc7AQAUwoPCm8KdwprCnMKEwoPChcKbwo0BAEZMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvUG9wdXBBbmltYXRpb247AQAUwo/Cn8KWwp/ClMKTwoTCisKDwpkBAD9MbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9TcGVlZDsBABTCjMKFwpzCmsKXwpnCk8KUwp3CgwEAE0xqYXZhL3V0aWwvSGFzaE1hcDsBAEhMamF2YS91dGlsL0hhc2hNYXA8TGphdmEvaW8vRmlsZTtMbmV0L21pbmVjcmFmdC91dGlsL1Jlc291cmNlTG9jYXRpb247PjsBABTCjMKUwoPCg8KFwpDCm8KIwo7CnQEAPkxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9HaWFudHM7AQAUwovCg8KcwpPCn8KLwprCk8KXwpABAD9MbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9QaGFzZTsBABTCnMKCwo/Cg8KRwobClsKJwozClAEAFMKQwpPCjcKUwpvCmcKKwo7ClcKYAQA+TG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvTW9kbGVzczsBABTCncKXwpDCm8KEwp3CksKLwofCjgEARExtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL2NvbWJhdC9UYXJnZXRTdHJhZmU7AQAUwpzCg8KIwobCm8KPwo/ChsKNwoQBAD9MbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9EaXNhYmxlcjsBABTCkcKFwojCh8KdwpPCjcKSwp3CkwEAPkxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9DYW1lcmE7AQAUwovCkMKXwozCjsKfwprClMKOwpsBADtMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9EZXJwOwEAAm1jAQAgTG5ldC9taW5lY3JhZnQvY2xpZW50L01pbmVjcmFmdDsBABTCncKFwofCn8KJwoXCiMKbwp3CiwEAQUxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21vdmVtZW50L0d1aU1vdmU7AQAUwp3CnMKCwo3Cg8KEwoPCksKMwogBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvSGl0Ym94ZXM7AQAUwpfCi8KZwpvCicKEwozCk8KfwpwBAEhMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvSW52ZW50b3J5RGlzcGxheTsBABTClMKSwpLCjMKZwpHClsKMwoXClAEAO0xtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL1Rlc3Q7AQAUwoXCkcKSwp/Cg8KFwpzCh8KTwpUBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvVmVsb2NpdHk7AQAUwp7CjsKUwpDChsKIwojCmsKawpUBAEJMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tYWNyby9BdXRvU3Vtb0JvdDsBABTCk8KFwpvChsKGwpbChMKcwpPCjQEAPkxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL1BWUEluZm87AQAUwoPClMKQwpnChMKKwpHChsKcwp0BADtMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvR3VpOwEAFMKGwo/CjMKKwpHCksKOwo7CjsKXAQBCTG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL0FuaW1hdGlvbnM7AQAUwpvCj8KSwp3ClsKXwpHCj8KMwp8BACpMbWUvb3JpbmdvL29yaW5nb2NsaWVudC91dGlscy9FbnRpdHlVdGlsczsBABTCh8KNwo/ChcKQwpvCg8KDworCmgEAQUxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21hY3JvL0FPVFZSZXR1cm47AQAUwo/CksKFwobCjsKawpbCmcKfwosBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvTm9Sb3RhdGU7AQAUwpnCmsKdwofCn8KNwo3ChMKYwpIBABTCkcKSwoXCmsKJwpLCnMKKwojCiQEAQUxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9OaWNrSGlkZXI7AQAUwozCm8KdwofCmMKGwoLCmMKFwowBAD9MbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvRnJlZUNhbTsBABTCksKDwoLCjsKVwo/CmMKbwoXCjgEAPUxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL2NvbWJhdC9SZWFjaDsBABTCh8KSwpPCksKRwo/Cj8KDwp/CkwEAQUxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3BsYXllci9GYXN0QnJlYWs7AQAUwoPCjsKSwofChsKbwovCisKFwpUBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BaW1ib3Q7AQAUwo7ClsKSwoXCh8KEwoLCkMKFwpkBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvS2lsbEF1cmE7AQAUwpLChcKWwo3CksKDwozClMKdwpgBABVMamF2YS91dGlsL0FycmF5TGlzdDsBADRMamF2YS91dGlsL0FycmF5TGlzdDxMbmV0L21pbmVjcmFmdC91dGlsL0Jsb2NrUG9zOz47AQAUwobCg8KJwoTCjMKFwpbCkMKOwoUBAENMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tYWNyby9NaXRocmlsTWFjcm87AQAUwo7CmcKZwo7CmMKRwpjChMKXwo4BACtMamF2YS91dGlsL2NvbmN1cnJlbnQvQ29weU9uV3JpdGVBcnJheUxpc3Q7AQBfTGphdmEvdXRpbC9jb25jdXJyZW50L0NvcHlPbldyaXRlQXJyYXlMaXN0PExtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9Nb2R1bGU7PjsBABTCm8KOwo7Cn8KbwpTChMKawp/CnQEAQExtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL2NvbWJhdC9CbG9ja0hpdDsBABTCncKKwpvCi8KawovChcKSwonCiAEAAVoBABTCgsKRwo3CmcKawoLClcKIwpLCnAEAQExtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21vdmVtZW50L05vU2xvdzsBABTCk8KYwp3CksKdwo/ChcKEwo/ChQEAFMKLwp7CncKYwprCkMKcwofChsKXAQA8TG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL1hSYXk7AQAUwpXCjsKRwpHCgsKGwoXChsKGwokBACtMamF2YS91dGlsL0FycmF5TGlzdDxMamF2YS9sYW5nL1J1bm5hYmxlOz47AQAUwpHCicKZwp7CnMKbwoXClcKJwo8BAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9TcHJpbnQ7AQAUwovCicKTwozClcKVwoPCl8KPwoUBAERMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvRW5jaGFudEdsaW50OwEAFMKZwpHCkcKcwpvCmcKZwpfCm8KOAQBITG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL0N1c3RvbUludGVyZmFjZXM7AQAUwp/CncKSwo7CisKfwo7CjsKewpYBAERMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9Eb2pvSGVscGVyOwEAFMKdworCkcKNwofCk8KcwonCisKIAQBCTG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvbW92ZW1lbnQvU2NhZmZvbGQ7AQAUwo7Cj8KGwojCicKZwp3ClcKdwpEBAEBMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9GbGlnaHQ7AQAUwonChMKHwpHClsKNwpHCnsKUwpUBACwoTGphdmEvaW8vSW5wdXRTdHJlYW07KUxqYXZhL25pby9CeXRlQnVmZmVyOwEAE2phdmEvaW8vSU9FeGNlcHRpb24HAH8BABVqYXZheC9pbWFnZWlvL0ltYWdlSU8HAIEBAARyZWFkAQA1KExqYXZhL2lvL0lucHV0U3RyZWFtOylMamF2YS9hd3QvaW1hZ2UvQnVmZmVyZWRJbWFnZTsMAIMAhAoAggCFAQAcamF2YS9hd3QvaW1hZ2UvQnVmZmVyZWRJbWFnZQcAhwEACGdldFdpZHRoAQADKClJDACJAIoKAIgAiwEACWdldEhlaWdodAwAjQCKCgCIAI4BAAJbSQcAkAEABmdldFJHQgEADChJSUlJW0lJSSlbSQwAkgCTCgCIAJQBABNqYXZhL25pby9CeXRlQnVmZmVyBwCWAQAIYWxsb2NhdGUBABgoSSlMamF2YS9uaW8vQnl0ZUJ1ZmZlcjsMAJgAmQoAlwCaAQATamF2YS9pby9JbnB1dFN0cmVhbQcAnAEABnB1dEludAwAngCZCgCXAJ8BAARmbGlwAQAXKClMamF2YS9uaW8vQnl0ZUJ1ZmZlcjsMAKEAogoAlwCjAQAGPGluaXQ+AQADKClWDAClAKYKAAQApwEAFMKcwpbCmMKCwonCk8KGwoTCk8KSAQA6KExqYXZhL3V0aWwvSGFzaE1hcDtMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspVgEAE2phdmEvbGFuZy9FeGNlcHRpb24HAKsBABFqYXZhL3V0aWwvSGFzaE1hcAcArQEAA2dldAEAJihMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7DACvALAKAK4AsQEAI25ldC9taW5lY3JhZnQvdXRpbC9SZXNvdXJjZUxvY2F0aW9uBwCzAQAMamF2YS9pby9GaWxlBwC1AQAXamF2YS9sYW5nL1N0cmluZ0J1aWxkZXIHALcKALgApwwANwA4CQACALoBAB5uZXQvbWluZWNyYWZ0L2NsaWVudC9NaW5lY3JhZnQHALwBAA1maWVsZF83MTQxMl9EAQAOTGphdmEvaW8vRmlsZTsMAL4AvwkAvQDAAQAHZ2V0UGF0aAEAFCgpTGphdmEvbGFuZy9TdHJpbmc7DADCAMMKALYAxAEABmFwcGVuZAEALShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmdCdWlsZGVyOwwAxgDHCgC4AMgBABsvY29uZmlnL09yaW5nb0NsaWVudC9jYXBlcy8IAMoBAAQucG5nCADMAQAQamF2YS9sYW5nL1N0cmluZwcAzgEAB3ZhbHVlT2YBACYoTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvU3RyaW5nOwwA0ADRCgDPANIBABUoTGphdmEvbGFuZy9TdHJpbmc7KVYMAKUA1AoAtgDVAQAGZXhpc3RzAQADKClaDADXANgKALYA2QEADGphdmEvbmV0L1VSTAcA2wEAGWh0dHA6Ly9uaWdlci41di5wbC9jYXBlcy8IAN0KANwA1QEACm9wZW5TdHJlYW0BABcoKUxqYXZhL2lvL0lucHV0U3RyZWFtOwwA4ADhCgDcAOIBAAZ0b1BhdGgBABYoKUxqYXZhL25pby9maWxlL1BhdGg7DADkAOUKALYA5gEAGGphdmEvbmlvL2ZpbGUvQ29weU9wdGlvbgcA6AEAE2phdmEvbmlvL2ZpbGUvRmlsZXMHAOoBAARjb3B5AQBHKExqYXZhL2lvL0lucHV0U3RyZWFtO0xqYXZhL25pby9maWxlL1BhdGg7W0xqYXZhL25pby9maWxlL0NvcHlPcHRpb247KUoMAOwA7QoA6wDuDAAlACYJAAIA8AEAC2NvbnRhaW5zS2V5AQAVKExqYXZhL2xhbmcvT2JqZWN0OylaDADyAPMKAK4A9AEADWZ1bmNfMTEwNDM0X0sBADgoKUxuZXQvbWluZWNyYWZ0L2NsaWVudC9yZW5kZXJlci90ZXh0dXJlL1RleHR1cmVNYW5hZ2VyOwwA9gD3CgC9APgBAAxvcmluZ29jbGllbnQIAPoBADRuZXQvbWluZWNyYWZ0L2NsaWVudC9yZW5kZXJlci90ZXh0dXJlL0R5bmFtaWNUZXh0dXJlBwD8AQAuKExqYXZhL2lvL0ZpbGU7KUxqYXZhL2F3dC9pbWFnZS9CdWZmZXJlZEltYWdlOwwAgwD+CgCCAP8BACEoTGphdmEvYXd0L2ltYWdlL0J1ZmZlcmVkSW1hZ2U7KVYMAKUBAQoA/QECAQA0bmV0L21pbmVjcmFmdC9jbGllbnQvcmVuZGVyZXIvdGV4dHVyZS9UZXh0dXJlTWFuYWdlcgcBBAEADWZ1bmNfMTEwNTc4X2EBAG8oTGphdmEvbGFuZy9TdHJpbmc7TG5ldC9taW5lY3JhZnQvY2xpZW50L3JlbmRlcmVyL3RleHR1cmUvRHluYW1pY1RleHR1cmU7KUxuZXQvbWluZWNyYWZ0L3V0aWwvUmVzb3VyY2VMb2NhdGlvbjsMAQYBBwoBBQEIAQADcHV0AQA4KExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsMAQoBCwoArgEMAQAabWUvb3JpbmdvL29yaW5nb2NsaWVudC9LRVkHAQ4BABTChMKJwoPClsKXworCnMKFwofCkAwBEAAmCQEPAREBAA9wcmludFN0YWNrVHJhY2UMARMApgoArAEUAQAQamF2YS9sYW5nL1N5c3RlbQcBFgEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwwBGAEZCQEXARoBABNFcnJvciBsb2FkaW5nIGNhcGUgCAEcAQATamF2YS9pby9QcmludFN0cmVhbQcBHgEAB3ByaW50bG4MASAA1AoBHwEhAQAUwojChcKHwpLCiMKEwo7ChsKUwowBAEMoTG5ldC9taW5lY3JhZnRmb3JnZS9mbWwvY29tbW9uL2V2ZW50L0ZNTFBvc3RJbml0aWFsaXphdGlvbkV2ZW50OylWAQAwTG5ldC9taW5lY3JhZnRmb3JnZS9mbWwvY29tbW9uL01vZCRFdmVudEhhbmRsZXI7AQBDbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvc2V0dGluZ3MvaW1wbC9Nb2RlU2V0dGluZwcBJgEAFMKOwpDChMKOwo7Ck8KdwoPCkMKSDAEoAKYKAScBKQEAFMKJwpXChcKMwp/CisKQwprCkcKTAQATamF2YS9sYW5nL1Rocm93YWJsZQcBLAEADENPTVBVVEVSTkFNRQgBLgEABmdldGVudgEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7DAEwATEKARcBMgEACXVzZXIubmFtZQgBNAEAC2dldFByb3BlcnR5DAE2ATEKARcBNwEAFFBST0NFU1NPUl9JREVOVElGSUVSCAE5AQAPUFJPQ0VTU09SX0xFVkVMCAE7AQADTUQ1CAE9AQAbamF2YS9zZWN1cml0eS9NZXNzYWdlRGlnZXN0BwE/AQALZ2V0SW5zdGFuY2UBADEoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL3NlY3VyaXR5L01lc3NhZ2VEaWdlc3Q7DAFBAUIKAUABQwEACGdldEJ5dGVzAQAEKClbQgwBRQFGCgDPAUcBAAZ1cGRhdGUBAAUoW0IpVgwBSQFKCgFAAUsBAAZkaWdlc3QMAU0BRgoBQAFOAQACW0IHAVABABFqYXZhL2xhbmcvSW50ZWdlcgcBUgEAC3RvSGV4U3RyaW5nAQAVKEkpTGphdmEvbGFuZy9TdHJpbmc7DAFUAVUKAVMBVgEABmxlbmd0aAwBWACKCgDPAVkBABwoQylMamF2YS9sYW5nL1N0cmluZ0J1aWxkZXI7DADGAVsKALgBXAEADWZ1bmNfMTEwNDMyX0kBAB4oKUxuZXQvbWluZWNyYWZ0L3V0aWwvU2Vzc2lvbjsMAV4BXwoAvQFgAQAabmV0L21pbmVjcmFmdC91dGlsL1Nlc3Npb24HAWIBAA1mdW5jXzExMTI4NV9hDAFkAMMKAWMBZQEAIWh0dHBzOi8vcGFzdGViaW4uY29tL3Jhdy9XOW1TVnRyNQgBZwEABmdldEtleQwBaQDDCgEPAWoBAAZmb3JtYXQBADkoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL1N0cmluZzsMAWwBbQoAzwFuAQAWamF2YS9pby9CdWZmZXJlZFJlYWRlcgcBcAEAGWphdmEvaW8vSW5wdXRTdHJlYW1SZWFkZXIHAXIBABgoTGphdmEvaW8vSW5wdXRTdHJlYW07KVYMAKUBdAoBcwF1AQATKExqYXZhL2lvL1JlYWRlcjspVgwApQF3CgFxAXgBAAhyZWFkTGluZQwBegDDCgFxAXsMACwAIAkAAgF9AQABMQgBfwEABmVxdWFscwwBgQDzCgDPAYIBAA1maWVsZF83MTQ3NF95AQAsTG5ldC9taW5lY3JhZnQvY2xpZW50L3NldHRpbmdzL0dhbWVTZXR0aW5nczsMAYQBhQkAvQGGAQAIPGNsaW5pdD4BABnCp2JPcmluZ29DbGllbnQgwqczwrsgwqc3CAGJDABRACAJAAIBiwgACwwAHwAgCQACAY4IAAcMAGwAIAkAAgGRAQAobWUvb3JpbmdvL29yaW5nb2NsaWVudC91dGlscy9FbnRpdHlVdGlscwcBkwoBlACnDABLAEwJAAIBlgEADGZ1bmNfNzE0MTBfeAEAIigpTG5ldC9taW5lY3JhZnQvY2xpZW50L01pbmVjcmFmdDsMAZgBmQoAvQGaAQApamF2YS91dGlsL2NvbmN1cnJlbnQvQ29weU9uV3JpdGVBcnJheUxpc3QHAZwKAZ0ApwwAYwBkCQACAZ8BADltZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9HdWkHAaEKAaIApwwARwBICQACAaQBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL2NvbWJhdC9LaWxsQXVyYQcBpgoBpwCnDABcAF0JAAIBqQEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL1ZlbG9jaXR5BwGrCgGsAKcMAEEAQgkAAgGuAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BaW1ib3QHAbAKAbEApwwAWgBbCQACAbMBADxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL01vZGxlc3MHAbUKAbYApwwALQAuCQACAbgBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21vdmVtZW50L05vU2xvdwcBugoBuwCnDABqAGsJAAIBvQEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvbW92ZW1lbnQvU3ByaW50BwG/CgHAAKcMAHEAcgkAAgHCAQA7bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvUmVhY2gHAcQKAcUApwwAVgBXCQACAccBAEBtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21hY3JvL0F1dG9TdW1vQm90BwHJCgHKAKcMAEMARAkAAgHMAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvRmFzdEJyZWFrBwHOCgHPAKcMAFgAWQkAAgHRAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tYWNyby9BT1RWUmV0dXJuBwHTCgHUAKcMAE0ATgkAAgHWAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvTmlja0hpZGVyBwHYCgHZAKcMAFIAUwkAAgHbAQBAbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvQW5pbWF0aW9ucwcB3QoB3gCnDABJAEoJAAIB4AEAPG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL0NhbWVyYQcB4goB4wCnDAAzADQJAAIB5QEAQW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvbWFjcm8vTWl0aHJpbE1hY3JvBwHnCgHoAKcMAGEAYgkAAgHqAQA5bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9EZXJwBwHsCgHtAKcMADUANgkAAgHvAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvSGl0Ym94ZXMHAfEKAfIApwwAOwA8CQACAfQBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3BsYXllci9Ob1JvdGF0ZQcB9goB9wCnDABPAFAJAAIB+QEAPW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svUGhhc2UHAfsKAfwApwwAKgArCQACAf4BAD1tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9GcmVlQ2FtBwIACgIBAKcMAFQAVQkAAgIDAQA8bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvR2lhbnRzBwIFCgIGAKcMACgAKQkAAgIIAQBGbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvQ3VzdG9tSW50ZXJmYWNlcwcCCgoCCwCnDAB1AHYJAAICDQEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvY29tYmF0L0Jsb2NrSGl0BwIPCgIQAKcMAGYAZwkAAgISAQA9bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9TcGVlZAcCFAoCFQCnDAAjACQJAAICFwEAOW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvVGVzdAcCGQoCGgCnDAA/AEAJAAICHAEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL05vUmVuZGVyBwIeCgIfAKcMABsAHAkAAgIhAQBCbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvVGFyZ2V0U3RyYWZlBwIjCgIkAKcMAC8AMAkAAgImAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9HdWlNb3ZlBwIoCgIpAKcMADkAOgkAAgIrAQBCbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9Eb2pvSGVscGVyBwItCgIuAKcMAHcAeAkAAgIwAQBEbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvUG9wdXBBbmltYXRpb24HAjIKAjMApwwAIQAiCQACAjUBAD1tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0Rpc2FibGVyBwI3CgI4AKcMADEAMgkAAgI6AQBAbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9tb3ZlbWVudC9TY2FmZm9sZAcCPAoCPQCnDAB5AHoJAAICPwEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvbW92ZW1lbnQvRmxpZ2h0BwJBCgJCAKcMAHsAfAkAAgJEAQBGbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvSW52ZW50b3J5RGlzcGxheQcCRgoCRwCnDAA9AD4JAAICSQEAOm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL1hSYXkHAksKAkwApwwAbQBuCQACAk4BAEFtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0x1bmFyU3Bvb2ZlcgcCUAoCUQCnDAAZABoJAAICUwEAQG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL0Z1bGxCcmlnaHQHAlUKAlYApwwAHQAeCQACAlgBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9FbmNoYW50R2xpbnQHAloKAlsApwwAcwB0CQACAl0BADxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL1BWUEluZm8HAl8KAmAApwwARQBGCQACAmIBABNqYXZhL3V0aWwvQXJyYXlMaXN0BwJkCgJlAKcMAF4AXwkAAgJnDABoAGkJAAICaQwAbwBfCQACAmsKAK4ApwEAFMKcwovCm8KHwozCn8KYwpHCmMKRAQBCKExuZXQvbWluZWNyYWZ0Zm9yZ2UvZm1sL2NvbW1vbi9ldmVudC9GTUxQcmVJbml0aWFsaXphdGlvbkV2ZW50OylWAQAUL2NvbmZpZy9PcmluZ29DbGllbnQIAnABAAVta2RpcgwCcgDYCgC2AnMBABovY29uZmlnL09yaW5nb0NsaWVudC9jYXBlcwgCdQEAHC9jb25maWcvT3JpbmdvQ2xpZW50L2NvbmZpZ3MIAncBACAvY29uZmlnL09yaW5nb0NsaWVudC9pbnN1bHRzLnR4dAgCeQEADWNyZWF0ZU5ld0ZpbGUMAnsA2AoAtgJ8AQA3bWUvb3JpbmdvL29yaW5nb2NsaWVudC9ldmVudHMvaW1wbC9Nb3ZlU3RhdGVVcGRhdGVFdmVudAcCfgEAFMKFwoLCgsKNwofCj8KTwoTChMKHDAKAAKYKAn8CgQwBKwCmCgACAoMBADBtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9Nb2R1bGUHAoUBABTCk8KFwonCnsKIwobCjcKOwp3CigEABChaKVYMAocCiAoChgKJAQADYWRkDAKLAPMKAZ0CjAEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0FudGlWb2lkBwKOCgKPAKcBAD9tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0FudGlOaWNrZXIHApEKApIApwEARG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svVGVybWluYWxBdXJhBwKUCgKVAKcBAD9tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0NoYXRCeXBhc3MHApcKApgApwEARm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svVGVybWluYXRvckF1cmEHApoKApsApwEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0F1dG9FY2hvBwKdCgKeAKcBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3NreWJsb2NrL1NlY3JldEF1cmEHAqAKAqEApwEAQG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL0R1bmdlb25FU1AHAqMKAqQApwEAQG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvbW92ZW1lbnQvU2FmZVdhbGsHAqYKAqcApwEASm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svUmVtb3ZlQW5ub3lpbmdNb2JzBwKpCgKqAKcBAD1tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21hY3JvL0F1dG9GaXNoBwKsCgKtAKcBAENtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3NreWJsb2NrL0dob3N0QmxvY2tzBwKvCgKwAKcBAEBtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL2NvbWJhdC9TdW1vRmVuY2VzBwKyCgKzAKcBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3NreWJsb2NrL0NyaW1zb25RT0wHArUKArYApwEAP21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvVG50UnVuUGluZwcCuAoCuQCnAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BdXRvUzEHArsKArwApwEAQG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0ludk1hbmFnZXIHAr4KAr8ApwEAQm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0NoZXN0U3RlYWxlcgcCwQoCwgCnAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvUGxheWVyRVNQBwLECgLFAKcBADptZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL1ZDbGlwBwLHCgLIAKcBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9DaGluYUhhdAcCygoCywCnAQBIbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvUmljaFByZXNlbmNlTW9kdWxlBwLNCgLOAKcBAD9tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9DdXN0b21FU1AHAtAKAtEApwEARm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svQXV0b1JvZ3VlU3dvcmQHAtMKAtQApwEARW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvR3Vlc3NUaGVCdWlsZEFGSwcC1goC1wCnAQBBbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9Tbm93YmFsbHMHAtkKAtoApwEAQ21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svSWNlRmlsbEhlbHAHAtwKAt0ApwEAKm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvVXBkYXRlcgcC3wEAFMKcworChMKdwovCicKHwpPCn8KSAQBJKClMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvU2VydmVyUm90YXRpb25zOwwC4QLiCgLgAuMBACdtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL0NvbW1hbmQHAuUBABTCn8KGwojChsKDwoXCjsKUwo3CjwEAQygpTG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL1RhcmdldEhVRDsMAucC6AoC5gLpAQA6bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvV1RhcAcC6woC7ACnAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvQXV0b1Rvb2wHAu4KAu8ApwEAQW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvU2VydmVyQmVhbWVyBwLxCgLyAKcBADptZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0JsaW5rBwL0CgL1AKcBAEBtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9Nb3Rpb25CbHVyBwL3CgL4AKcBAENtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL011cmRlcmVyRmluZGVyBwL6CgL7AKcBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9DaGVzdEVTUAcC/QoC/gCnAQBDbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9Cb25lVGhyb3dlcgcDAAoDAQCnAQA9bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvQXV0b1BvdAcDAwoDBACnAQA8bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvTm9GYWxsBwMGCgMHAKcBADxtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21vdmVtZW50L1N0ZXAHAwkKAwoApwEAQm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svQW50aU51a2ViaQcDDAoDDQCnAQA7bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvVHJpYWwHAw8KAxAApwEAQW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvY29tYmF0L0F1dG9DbGlja2VyBwMSCgMTAKcBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL1N0YWZmQW5hbHlzZXIHAxUKAxYApwEAP21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0FybW9yU3dhcAcDGAoDGQCnAQA/bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9jb21iYXQvQWltQXNzaXN0BwMbCgMcAKcBABTCnsKZwpzCiMKUwozCkcKdwo7CggEAQSgpTG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvY29tYmF0L0FudGlCb3Q7DAMeAx8KAvIDIAEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0F1dG9IZWFsBwMiCgMjAKcBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3JlbmRlci9OYW1ldGFncwcDJQoDJgCnAQBEbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9CbGF6ZVN3YXBwZXIHAygKAykApwEAOm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvVGltZXIHAysKAywApwEAQG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvS2lsbEluc3VsdHMHAy4KAy8ApwEAO21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvRGVsYXlzBwMxAQAUwpHCncKYwo/Cn8KPwoTChcKNwo0BAD1MbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9EZWxheXM7DAMzAzQJAzIDNQEAPG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvQnJlYWtlcgcDNwoDOACnAQBBbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9yZW5kZXIvSGlkZVBsYXllcnMHAzoKAzsApwEAQm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvU2ltdWxhdG9yQXVyYQcDPQoDPgCnAQA8bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9SZXNldFZMBwNACgNBAKcBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3NreWJsb2NrL1JvZFN0YWNrZXIHA0MKA0QApwEAPm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvTmFtZXNPbmx5BwNGCgNHAKcBAD1tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL05vQ2FycGV0BwNJCgNKAKcBAEVtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL3NreWJsb2NrL0NyeXN0YWxQbGFjZXIHA0wKA00ApwEAOm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvSENsaXAHA08KA1AApwEAP21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvY29tYmF0L0NyaXRpY2FscwcDUgoDUwCnAQBAbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BdXRvUXVpegcDVQoDVgCnAQBDbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BdXRvV2VpcmRvcwcDWAoDWQCnAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9vdGhlci9DYWtlTnVrZXIHA1sKA1wApwEAQW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svQXV0b0FsaWduBwNeCgNfAKcBAD1tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21vdmVtZW50L1NuZWFrBwNhCgNiAKcBAEJtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL290aGVyL0F1dG9SZWNvbm5lY3QHA2QKA2UApwEARG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvb3RoZXIvTGlnaHRuaW5nRGV0ZWN0BwNnCgNoAKcBAD5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL21vZHVsZS9pbXBsL21hY3JvL1JldlRyYWRlcgcDagoDawCnAQBAbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9za3libG9jay9BdXRvTWFzawcDbQoDbgCnAQA+bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvQW50aU9iYnkHA3AKA3EApwEAPW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcGxheWVyL0F1dG9VSEMHA3MKA3QApwEARW1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvc2t5YmxvY2svQXV0b1Rlcm1pbmFscwcDdgoDdwCnAQAtbWUvb3JpbmdvL29yaW5nb2NsaWVudC91dGlscy9zaGFkZXIvQmx1clV0aWxzBwN5AQAQcmVnaXN0ZXJMaXN0ZW5lcgwDewCmCgN6A3wBAAhpdGVyYXRvcgEAFigpTGphdmEvdXRpbC9JdGVyYXRvcjsMA34DfwoBnQOAAQASamF2YS91dGlsL0l0ZXJhdG9yBwOCAQAHaGFzTmV4dAwDhADYCwODA4UBAARuZXh0AQAUKClMamF2YS9sYW5nL09iamVjdDsMA4cDiAsDgwOJAQAobmV0L21pbmVjcmFmdGZvcmdlL2NvbW1vbi9NaW5lY3JhZnRGb3JnZQcDiwEACUVWRU5UX0JVUwEANUxuZXQvbWluZWNyYWZ0Zm9yZ2UvZm1sL2NvbW1vbi9ldmVudGhhbmRsZXIvRXZlbnRCdXM7DAONA44JA4wDjwEAM25ldC9taW5lY3JhZnRmb3JnZS9mbWwvY29tbW9uL2V2ZW50aGFuZGxlci9FdmVudEJ1cwcDkQEACHJlZ2lzdGVyAQAVKExqYXZhL2xhbmcvT2JqZWN0OylWDAOTA5QKA5IDlQEANG1lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9KZXJyeUJveENvbW1hbmQHA5cKA5gApwEAFMKZwozClMKFwonCg8KOwo7CksKSAQAsKExtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL0NvbW1hbmQ7KVYMA5oDmwoBtgOcAQAxbWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1N0YWxrQ29tbWFuZAcDngoDnwCnAQA0bWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1dhcmRyb2JlQ29tbWFuZAcDoQoDogCnAQAwbWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL0hlbHBDb21tYW5kBwOkCgOlAKcBADdtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL2ltcGwvQXJtb3JTdGFuZHNDb21tYW5kBwOnCgOoAKcBADVtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL2ltcGwvQ2hlY2tuYW1lQ29tbWFuZAcDqgoDqwCnAQAwbWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL0NsaXBDb21tYW5kBwOtCgOuAKcBADJtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL2ltcGwvQ29uZmlnQ29tbWFuZAcDsAoDsQCnAQA0bWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL0ZpcmV3b3JrQ29tbWFuZAcDswoDtACnAQA0bWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1NldHRpbmdzQ29tbWFuZAcDtgoDtwCnAQAvbWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1NheUNvbW1hbmQHA7kKA7oApwEANW1lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9DdXN0b21FU1BDb21tYW5kBwO8CgO9AKcBADBtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL2ltcGwvWFJheUNvbW1hbmQHA78KA8AApwEAMW1lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9IQ2xpcENvbW1hbmQHA8IKA8MApwEAMW1lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9OYW1lc0NvbW1hbmQHA8UKA8YApwEANm1lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9QbGF5ZXJMaXN0Q29tbWFuZAcDyAoDyQCnAQAxbWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1VIQ1RwQ29tbWFuZAcDywoDzACnAQA2bWUvb3JpbmdvL29yaW5nb2NsaWVudC9jb21tYW5kcy9pbXBsL1RocmVlRENsaXBDb21tYW5kBwPOCgPPAKcBADBtZS9vcmluZ28vb3JpbmdvY2xpZW50L2NvbW1hbmRzL2ltcGwvVGVzdENvbW1hbmQHA9EKA9IApwEANW1lL29yaW5nby9vcmluZ29jbGllbnQvdWkvbm90aWZpY2F0aW9ucy9Ob3RpZmljYXRpb25zBwPUCgPVAKcBAChtZS9vcmluZ28vb3JpbmdvY2xpZW50L3V0aWxzL1NlcnZlclV0aWxzBwPXAQAUwojCnsKewpvCnMKDwofCmcKRwpQBACpMbWUvb3JpbmdvL29yaW5nb2NsaWVudC91dGlscy9TZXJ2ZXJVdGlsczsMA9kD2gkD2APbCgLgAKcBAC5tZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL0F0dGFja1F1ZXVlBwPeCgPfAKcBACptZS9vcmluZ28vb3JpbmdvY2xpZW50L3V0aWxzL1NreWJsb2NrVXRpbHMHA+EKA+IApwEAKm1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvQnV0dG9ucwcD5AoD5QCnAQA0bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9CYWNrZ3JvdW5kUHJvY2VzcwcD5woD6ACnAQA9bWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvaW1wbC9wbGF5ZXIvRmFzdFVzZQcD6gEAFMKYwonChcKKwpvCnMKewo/CksKUDAPsAKYKA+sD7QEAMm1lL29yaW5nby9vcmluZ29jbGllbnQvdWkvaHVkL2ltcGwvVGFyZ2V0Q29tcG9uZW50BwPvAQAUwpvClsKEwo/CjMKTwoXCmsKYwpwBADRMbWUvb3JpbmdvL29yaW5nb2NsaWVudC91aS9odWQvaW1wbC9UYXJnZXRDb21wb25lbnQ7DAPxA/IJA/AD8wEAP21lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL2ltcGwvcmVuZGVyL1RhcmdldEhVRAcD9QEAFMKHwpXChsKdwpTChsKVwpnCkcKOAQBHTG1lL29yaW5nby9vcmluZ29jbGllbnQvcW9sZmVhdHVyZXMvbW9kdWxlL3NldHRpbmdzL2ltcGwvTnVtYmVyU2V0dGluZzsMA/cD+AkD9gP5AQBFbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvc2V0dGluZ3MvaW1wbC9OdW1iZXJTZXR0aW5nBwP7AQAUwpjCmsKUwpbCmcKFwpTChMKJwp4BAAMoKUQMA/0D/goD/AP/AQAUwo7Cn8KGwofCicKdwp/CnsKFwpkMBAED+AkD9gQCAQAnbWUvb3JpbmdvL29yaW5nb2NsaWVudC91aS9odWQvQ29tcG9uZW50BwQEAQAUwpHCnsKRwpzCkMKfwo3ChsKPwoMBAC0oREQpTG1lL29yaW5nby9vcmluZ29jbGllbnQvdWkvaHVkL0NvbXBvbmVudDsMBAYEBwoEBQQIAQAUwpDCgsKSwonCm8KIwprChsKKwpIBAEhMbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvc2V0dGluZ3MvaW1wbC9Cb29sZWFuU2V0dGluZzsMBAoECwkCOAQMAQBGbWUvb3JpbmdvL29yaW5nb2NsaWVudC9xb2xmZWF0dXJlcy9tb2R1bGUvc2V0dGluZ3MvaW1wbC9Cb29sZWFuU2V0dGluZwcEDgEAFMKZwpfCjMKJwpXCi8KawoLCm8KcDAQQANgKBA8EEQEAFMKKwozClMKSwprClMKIwp3CnMKYDAQTAogKBA8EFAEAFMKMwo3Cl8KTwpXCkMKFwpfChsKJDAQWANgKAoYEFwEAFMKGwoTCjsKPwp7CmcKYwpHCi8KPDAQZAKYKAoYEGgEACU9yaW5nb0RldggEHAEAL21lL29yaW5nby9vcmluZ29jbGllbnQvY29tbWFuZHMvaW1wbC9CYW5Db21tYW5kBwQeCgQfAKcBADNtZS9vcmluZ28vb3JpbmdvY2xpZW50L3FvbGZlYXR1cmVzL0xvZ2luV2l0aFNlc3Npb24HBCEKBCIApwEAKG1lL29yaW5nby9vcmluZ29jbGllbnQvdXRpbHMvUGFja2V0VXRpbHMHBCQBABTCnMKewpTChsKRwpnCjMKbwpzCjAwEJgCmCgQlBCcBAAlTaWduYXR1cmUBAARDb2RlAQANU3RhY2tNYXBUYWJsZQEACkV4Y2VwdGlv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";
      if (!str.isEmpty())
        return Base64.getDecoder().decode(str); 
    } 
    return paramArrayOfbyte;
  }
}