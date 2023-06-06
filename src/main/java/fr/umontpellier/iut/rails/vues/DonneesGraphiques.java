package fr.umontpellier.iut.rails.vues;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cette classe permet de construire les rectangles et les cercles correspondant aux routes et villes,
 * à placer sur le plateau.
 *
 * Vous n'avez pas à modifier ces données, à part peut-être la donnée pourcentageEcran
 */
public final class DonneesGraphiques {
    public final static Map<String, ArrayList<DonneesSegments>> routes;
    public final static Map<String, DonneesCerclesPorts> villes;
    public final static Map<String, DonneesCerclesPorts> ports;

    private final static Image map = new Image("images/map.jpg");

    public static final double pourcentageEcran = .8, // permet de définir une proportion de la scène par rapport à l'écran de l'utilisateur
            largeurInitialePlateau = map.getWidth(),// 1920.0 largeur de l'image
            hauteurInitialePlateau = map.getHeight(),// 1069.0 hauteur de l'image
            largeurRectangle = 46,
            hauteurRectangle = 14,
            rayonInitial = 12;

    static {
        routes = new LinkedHashMap<>();
        routes.put("R1", new ArrayList<>(){{
            add(new DonneesSegments(970, 445.5,  0.870903457075653));
        }});
        routes.put("R2", new ArrayList<>(){{
            add(new DonneesSegments(825, 466.4464292526245, 0.18534794999569476));
            add(new DonneesSegments(878.5, 472.9464292526245, 0.08145204430587097));
            add(new DonneesSegments(933.5, 474.9464292526245, 0));
        }});
        routes.put("R3", new ArrayList<>(){{
            add(new DonneesSegments(1033.5, 518.7321472167969, 1.1341691669813554));
            add(new DonneesSegments(1055.5, 567.2321472167969, 1.142532173918242));
        }});
        routes.put("R4", new ArrayList<>(){{
            add(new DonneesSegments(1014.5, 526.5, 1.125495282907211));
            add(new DonneesSegments(1036.5, 576, 1.171280832795522));
        }});
        routes.put("R5", new ArrayList<>(){{
            add(new DonneesSegments(1061.5, 472.58928298950195, -0.30893074392374165));
        }});
        routes.put("R6", new ArrayList<>(){{
            add(new DonneesSegments(1056, 454, -0.3028848683749714));
        }});
        routes.put("R7", new ArrayList<>(){{
            add(new DonneesSegments(44.5, 202.08928298950195, -0.4023210978604407));
            add(new DonneesSegments(93.5, 181.58928298950195, -0.3995154939993745));
            add(new DonneesSegments(143.5, 160.58928298950195, -0.3995154939993745));
            add(new DonneesSegments(192, 140.58928298950195, -0.4076315054576269));
            add(new DonneesSegments(240, 120.58928298950195, -0.3916999394374241));
            add(new DonneesSegments(290, 100.58928298950195, -0.3916999394374241));
        }});
        routes.put("R8", new ArrayList<>(){{
            add(new DonneesSegments(1721.7999877929688, 292, 0.25436805855326594));
            add(new DonneesSegments(1774.7999877929688, 293, -0.22527677921405528));
            add(new DonneesSegments(1818.7999877929688, 266.5, -0.8408966686431651));
        }});
        routes.put("R9", new ArrayList<>(){{
            add(new DonneesSegments(1468.2999877929688, 88, 0.06114816264930121));
            add(new DonneesSegments(1520.2999877929688, 94, 0.058755822715722696));
            add(new DonneesSegments(1573.7999877929688, 99.5, 0.07982998571223732));
            add(new DonneesSegments(1627.2999877929688, 105, 0.10168885176307704));
            add(new DonneesSegments(1679.7999877929688, 113.5, 0.24497866312686414));
            add(new DonneesSegments(1729.7999877929688, 129.5, 0.372987721800061));
            add(new DonneesSegments(1777.7999877929688, 153, 0.5010133868035895));
            add(new DonneesSegments(1822.7999877929688, 182.5, 0.668289418830584));
        }});
        routes.put("R10", new ArrayList<>(){{
            add(new DonneesSegments(40, 242.58928298950195, 0.05992815512120788));
            add(new DonneesSegments(76, 279.58928298950195, 1.4858945333451747));
        }});
        routes.put("R11", new ArrayList<>(){{
            add(new DonneesSegments(910.5, 315, 0.5933501461567182));
            add(new DonneesSegments(940, 361.5, 1.3352513460740334));
        }});
        routes.put("R12", new ArrayList<>(){{
            add(new DonneesSegments(844.5, 395, 0.7283173809911837));
            add(new DonneesSegments(894, 413.5, -0.039978687123290044));
        }});
        routes.put("R13", new ArrayList<>(){{
            add(new DonneesSegments(993.5, 408.4464292526245, -0.020405330686538086));
            add(new DonneesSegments(1047, 416.4464292526245, 0.25436805855326594));
        }});
        routes.put("R14", new ArrayList<>(){{
            add(new DonneesSegments(1474, 562.3035736083984, -0.8276265655104502));
        }});
        routes.put("R15", new ArrayList<>(){{
            add(new DonneesSegments(1459.2999877929688, 549.5, -0.8557046275160728));
        }});
        routes.put("R16", new ArrayList<>(){{
            add(new DonneesSegments(1413.5, 630.4464416503906, 1.8301280711955816));
            add(new DonneesSegments(1432.5, 672.4464416503906, 0.507098504392337));
        }});
        routes.put("R17", new ArrayList<>(){{
            add(new DonneesSegments(1475.5, 615, 0.30893074392374165));
            add(new DonneesSegments(1525.5, 607, -0.6393859056861729));
        }});
        routes.put("R18", new ArrayList<>(){{
            add(new DonneesSegments(1304, 510, -0.4076315054576269));
            add(new DonneesSegments(1360, 508, 0.3028848683749714));
            add(new DonneesSegments(1406, 540, 0.9151007005533605));
        }});
        routes.put("R19", new ArrayList<>(){{
            add(new DonneesSegments(1299.2999877929688, 534.5, -0.4023210978604407));
            add(new DonneesSegments(1354.2999877929688, 529, 0.26984915593846925));
            add(new DonneesSegments(1398.7999877929688, 562, 0.9481255380378293));
        }});
        routes.put("R20", new ArrayList<>(){{
            add(new DonneesSegments(1481, 475.01786041259766, -1.7326337580333195));
            add(new DonneesSegments(1473.5, 421.51786041259766, -1.6745886672519397));
        }});
        routes.put("R21", new ArrayList<>(){{
            add(new DonneesSegments(1493.5, 418.5, 1.4259838285559576));
            add(new DonneesSegments(1501.5, 471, 1.3815973046949284));
        }});
        routes.put("R22", new ArrayList<>(){{
            add(new DonneesSegments(1314.2857666015625, 424.0178680419922, -0.22527677921405528));
            add(new DonneesSegments(1366.7857666015625, 412.0178680419922, -0.2299044281319047));
            add(new DonneesSegments(1418.2857666015625, 401.0178680419922, -0.22527677921405528));
        }});
        routes.put("R23", new ArrayList<>(){{
            add(new DonneesSegments(1330, 309.08928298950195, 0.49934672168013006));
            add(new DonneesSegments(1376.5, 335.08928298950195, 0.5090708884223831));
            add(new DonneesSegments(1423.5, 361.08928298950195, 0.5090708884223831));
        }});
        routes.put("R24", new ArrayList<>(){{
            add(new DonneesSegments(1340, 291, 0.5369107427400456));
            add(new DonneesSegments(1387, 317, 0.48166367755486145));
            add(new DonneesSegments(1433, 343.5, 0.49934672168013006));
        }});
        routes.put("R25", new ArrayList<>(){{
            add(new DonneesSegments(1484.7999877929688, 225, 1.5707963267948966));
            add(new DonneesSegments(1483.7999877929688, 278.5, 1.5707963267948966));
            add(new DonneesSegments(1485.2999877929688, 330.5, 1.5499660067586796));
        }});
        routes.put("R26", new ArrayList<>(){{
            add(new DonneesSegments(528.5, 939.5, 0));
            add(new DonneesSegments(583, 939.5, 0));
            add(new DonneesSegments(635, 940, 0.020830320036217084));
            add(new DonneesSegments(688, 939, 0.020830320036217084));
            add(new DonneesSegments(742.5, 940.5, -0.0425275346197842));
            add(new DonneesSegments(796.5, 941, -0.020405330686538086));
            add(new DonneesSegments(848, 940.5, 0.04164257909858842));
        }});
        routes.put("R27", new ArrayList<>(){{
            add(new DonneesSegments(528, 919.5, -0.04164257909858842));
            add(new DonneesSegments(582.5, 920, -0.020405330686538086));
            add(new DonneesSegments(634, 919, -0.020830320036217084));
            add(new DonneesSegments(689, 920.5, 0));
            add(new DonneesSegments(741, 920, -0.021735706841792664));
            add(new DonneesSegments(795, 920.5, 0));
            add(new DonneesSegments(848.5, 920.5, -0.0407936828678654));
        }});
        routes.put("R28", new ArrayList<>(){{
            add(new DonneesSegments(491.5, 870.8035888671875, -0.9664908199286669));
        }});
        routes.put("R29", new ArrayList<>(){{
            add(new DonneesSegments(508, 881.5, -0.982793723247329));
        }});
        routes.put("R30", new ArrayList<>(){{
            add(new DonneesSegments(373.5, 963, 1.223734723255406));
            add(new DonneesSegments(412.5, 991.5, 0));
            add(new DonneesSegments(451.5, 964.5, -1.1631648213372698));
        }});
        routes.put("R31", new ArrayList<>(){{
            add(new DonneesSegments(386, 99.58928298950195, 0.2644883825492325));
            add(new DonneesSegments(437.5, 114.58928298950195, 0.25933174440068485));
            add(new DonneesSegments(489, 129.08928298950195, 0.28379410920832787));
            add(new DonneesSegments(540.5, 142.58928298950195, 0.26984915593846925));
            add(new DonneesSegments(590.5, 157.58928298950195, 0.25933174440068485));
            add(new DonneesSegments(643, 171.08928298950195, 0.24497866312686414));
        }});
        routes.put("R32", new ArrayList<>(){{
            add(new DonneesSegments(259.5, 273.58928298950195, -0.9314104211087237));
            add(new DonneesSegments(289.5, 227.58928298950195, -1.0796327285086362));
            add(new DonneesSegments(313, 179.58928298950195, -1.1902899496825317));
            add(new DonneesSegments(329.5, 127.58928298950195, -1.3009471708564273));
        }});
        routes.put("R33", new ArrayList<>(){{
            add(new DonneesSegments(939, 840.5, -0.8960553845713439));
            add(new DonneesSegments(971.5, 798, -0.8991901705411564));
            add(new DonneesSegments(1004, 756.5, -0.9025069079643125));
        }});
        routes.put("R34", new ArrayList<>(){{
            add(new DonneesSegments(954.5, 851.5, -0.8865015351337469));
            add(new DonneesSegments(988, 810.5, -0.9272952180016122));
            add(new DonneesSegments(1019.5, 769, -0.8991901705411564));
        }});
        routes.put("R35", new ArrayList<>(){{
            add(new DonneesSegments(888, 784.3035888671875, 1.4489538897749794));
            add(new DonneesSegments(894, 837.8035888671875, 1.4513674007765582));
        }});
        routes.put("R36", new ArrayList<>(){{
            add(new DonneesSegments(971.5, 913.5, 0.28950367196138826));
            add(new DonneesSegments(1023, 925.5, 0.2053953891897674));
            add(new DonneesSegments(1075, 937, 0.22527677921405528));
            add(new DonneesSegments(1126.5, 949, 0.22082876972934834));
            add(new DonneesSegments(1178.5, 962, 0.22082876972934834));
        }});
        routes.put("R37", new ArrayList<>(){{
            add(new DonneesSegments(966.5, 931.5, 0.2401710078601731));
            add(new DonneesSegments(1017.5, 945, 0.2299044281319047));
            add(new DonneesSegments(1069.5, 956.5, 0.21866894587394195));
            add(new DonneesSegments(1122, 969, 0.2347226124851971));
            add(new DonneesSegments(1173.5, 980.5, 0.20963984587421253));
        }});
        routes.put("R38", new ArrayList<>(){{
            add(new DonneesSegments(585, 834.3035888671875, 0.17809293823119754));
            add(new DonneesSegments(637.5, 842.3035888671875, 0.1418970546041639));
            add(new DonneesSegments(690, 850.8035888671875, 0.1586552621864014));
            add(new DonneesSegments(743, 858.8035888671875, 0.16514867741462683));
            add(new DonneesSegments(795.5, 867.3035888671875, 0.14784936525875556));
            add(new DonneesSegments(847.5, 875.3035888671875, 0.1418970546041639));
        }});
        routes.put("R39", new ArrayList<>(){{
            add(new DonneesSegments(581, 854.8035888671875, 0.19739555984988078));
            add(new DonneesSegments(634.5, 862.3035888671875, 0.18164883000001195));
            add(new DonneesSegments(687, 870.8035888671875, 0.16514867741462683));
            add(new DonneesSegments(739.5, 877.8035888671875, 0.16859693960942962));
            add(new DonneesSegments(792, 887.3035888671875, 0.1510153429213594));
            add(new DonneesSegments(844.5, 895.3035888671875, 0.1418970546041639));
        }});
        routes.put("R40", new ArrayList<>(){{
            add(new DonneesSegments(968, 880.3035888671875, -0.48166367755486145));
            add(new DonneesSegments(1016, 855.3035888671875, -0.48166367755486145));
            add(new DonneesSegments(1061.5, 830.8035888671875, -0.5090708884223831));
        }});
        routes.put("R41", new ArrayList<>(){{
            add(new DonneesSegments(445.5, 627, 0.06114816264930121));
            add(new DonneesSegments(499, 630, 0.06241880999595735));
            add(new DonneesSegments(552.5, 633, 0.06114816264930121));
            add(new DonneesSegments(605.5, 637, 0.020405330686538086));
            add(new DonneesSegments(658, 639, 0.05992815512120788));
            add(new DonneesSegments(711.5, 641.5, 0.0425275346197842));
            add(new DonneesSegments(764.5, 644.5, 0.08145204430587097));
        }});
        routes.put("R42", new ArrayList<>(){{
            add(new DonneesSegments(349.5, 723.8035888671875, -1.142532173918242));
            add(new DonneesSegments(371, 674.8035888671875, -1.1978086049948355));
        }});
        routes.put("R43", new ArrayList<>(){{
            add(new DonneesSegments(355, 668, 1.9890206563741257));
            add(new DonneesSegments(333, 716, 1.9890206563741257));
        }});
        routes.put("R44", new ArrayList<>(){{
            add(new DonneesSegments(228, 587, 0.2644883825492325));
            add(new DonneesSegments(279, 603, 0.31521469975071104));
            add(new DonneesSegments(330.5, 618.5, 0.34161549064780716));
        }});
        routes.put("R45", new ArrayList<>(){{
            add(new DonneesSegments(234.5, 568.5, 0.28950367196138826));
            add(new DonneesSegments(284.5, 583.5, 0.28950367196138826));
            add(new DonneesSegments(335, 600, 0.2644883825492325));
        }});
        routes.put("R46", new ArrayList<>(){{
            add(new DonneesSegments(351, 535.4464416503906, 0.25436805855326594));
            add(new DonneesSegments(379.5, 572.9464416503906, 1.5108681716736887));
        }});
        routes.put("R47", new ArrayList<>(){{
            add(new DonneesSegments(418, 664.4464416503906, 0.3916999394374241));
            add(new DonneesSegments(467, 691.9464416503906, 0.6435011087932844));
            add(new DonneesSegments(504, 733.4464416503906, 1.0269638704927742));
            add(new DonneesSegments(523.5, 784.9464416503906, 1.3063079442456642));
        }});
        routes.put("R48", new ArrayList<>(){{
            add(new DonneesSegments(435, 649, 0.37690927032482097));
            add(new DonneesSegments(482, 677, 0.6593100683328579));
            add(new DonneesSegments(519, 719.5, 1.0516502125483738));
            add(new DonneesSegments(540.5, 770, 1.2895641248766014));
        }});
        routes.put("R49", new ArrayList<>(){{
            add(new DonneesSegments(732.5, 501.9464416503906, 2.1077070695349422));
            add(new DonneesSegments(710, 552.4464416503906, 1.8207749482557192));
            add(new DonneesSegments(731.5, 600.9464416503906, 0.5090708884223831));
            add(new DonneesSegments(782, 622.9464416503906, 0.28379410920832787));
        }});
        routes.put("R50", new ArrayList<>(){{
            add(new DonneesSegments(780.2857666015625, 407.5178680419922, -1.0164888305933455));
        }});
        routes.put("R51", new ArrayList<>(){{
            add(new DonneesSegments(360.5, 501, -0.1418970546041639));
            add(new DonneesSegments(414.5, 494, -0.10168885176307704));
            add(new DonneesSegments(467, 488, -0.09966865249116204));
            add(new DonneesSegments(519.5, 482, -0.13640260440094745));
            add(new DonneesSegments(573, 475, -0.13381097747459075));
            add(new DonneesSegments(624.5, 469, -0.1418970546041639));
            add(new DonneesSegments(677.5, 463.5, -0.12697278961407893));
        }});
        routes.put("R52", new ArrayList<>(){{
            add(new DonneesSegments(1766.5, 929.8035888671875, 0.4899573262537283));
        }});
        routes.put("R53", new ArrayList<>(){{
            add(new DonneesSegments(1757, 947, 0.48166367755486145));
        }});
        routes.put("R54", new ArrayList<>(){{
            add(new DonneesSegments(1862.2999877929688, 957, -0.06114816264930121));
            add(new DonneesSegments(47, 950, -0.057628427477473966));
            add(new DonneesSegments(101.5, 944, -0.10168885176307704));
            add(new DonneesSegments(155, 939, -0.06241880999595735));
            add(new DonneesSegments(207.5, 934.5, -0.08490179344972194));
            add(new DonneesSegments(259.5, 929.5, -0.08145204430587097));
            add(new DonneesSegments(312.5, 923, -0.06374331253268707));
        }});
        routes.put("R55", new ArrayList<>(){{
            add(new DonneesSegments(1044, 660.8035888671875, 1.7294515889812982));
        }});
        routes.put("R56", new ArrayList<>(){{
            add(new DonneesSegments(1064, 663, 1.7326337580333195));
        }});
        routes.put("R57", new ArrayList<>(){{
            add(new DonneesSegments(1097.5, 725, 0.020405330686538086));
            add(new DonneesSegments(1150.5, 725, -0.020405330686538086));
            add(new DonneesSegments(1203, 726, 0.020830320036217084));
            add(new DonneesSegments(1257, 725.5, 0));
            add(new DonneesSegments(1310, 725.5, 0.039978687123290044));
            add(new DonneesSegments(1362, 725.5, -0.04164257909858842));
            add(new DonneesSegments(1417, 725, -0.019997333973150535));
        }});
        routes.put("R58", new ArrayList<>(){{
            add(new DonneesSegments(1098.2999877929688, 704, 0.020405330686538086));
            add(new DonneesSegments(1151.2999877929688, 703.5, 0));
            add(new DonneesSegments(1204.2999877929688, 703.5, 0));
            add(new DonneesSegments(1257.2999877929688, 704, 0.020405330686538086));
            add(new DonneesSegments(1310.7999877929688, 704, 0.019997333973150535));
            add(new DonneesSegments(1363.7999877929688, 704.5, 0));
            add(new DonneesSegments(1416.2999877929688, 703.5, -0.04441521524691084));
        }});
        routes.put("R59", new ArrayList<>(){{
            add(new DonneesSegments(941.2857666015625, 727.3035888671875, -0.14481249823893905));
            add(new DonneesSegments(994.2857666015625, 719.3035888671875, -0.14481249823893905));
        }});
        routes.put("R60", new ArrayList<>(){{
            add(new DonneesSegments(1102, 681.8035888671875, -0.19739555984988078));
            add(new DonneesSegments(1156, 668.8035888671875, -0.24497866312686414));
            add(new DonneesSegments(1202, 644.8035888671875, -0.7568345055586883));
            add(new DonneesSegments(1232, 600.3035888671875, -1.1902899496825317));
        }});
        routes.put("R61", new ArrayList<>(){{
            add(new DonneesSegments(1073, 757.5, 0.9272952180016122));
        }});
        routes.put("R62", new ArrayList<>(){{
            add(new DonneesSegments(1526, 713.3035888671875, 0.22527677921405528));
            add(new DonneesSegments(1578, 724.3035888671875, 0.21655030497608926));
        }});
        routes.put("R63", new ArrayList<>(){{
            add(new DonneesSegments(1561, 830.5, -0.8960553845713439));
            add(new DonneesSegments(1593.5, 789, -0.8991901705411564));
        }});
        routes.put("R64", new ArrayList<>(){{
            add(new DonneesSegments(1680.5, 729.5, -0.3016190462662397));
        }});
        routes.put("R65", new ArrayList<>(){{
            add(new DonneesSegments(1660, 798.5, 1.1978086049948355));
            add(new DonneesSegments(1679.5, 850, 1.2368512549500026));
        }});
        routes.put("R66", new ArrayList<>(){{
            add(new DonneesSegments(823.5, 276.58928298950195, 0.1418970546041639));
        }});
        routes.put("R67", new ArrayList<>(){{
            add(new DonneesSegments(825.5, 258, 0.18164883000001195));
        }});
        routes.put("R68", new ArrayList<>(){{
            add(new DonneesSegments(778.5, 320.58928298950195, 1.1505996131856213));
        }});
        routes.put("R69", new ArrayList<>(){{
            add(new DonneesSegments(798, 311, 1.1354430516425942));
        }});
        routes.put("R70", new ArrayList<>(){{
            add(new DonneesSegments(415, 371.5, -0.28379410920832787));
            add(new DonneesSegments(464.5, 356, -0.26984915593846925));
            add(new DonneesSegments(516.5, 341, -0.28123220191829523));
            add(new DonneesSegments(569, 326.5, -0.3217505543966422));
            add(new DonneesSegments(619, 312.5, -0.3080527810237764));
            add(new DonneesSegments(670.5, 298.5, -0.3016190462662397));
            add(new DonneesSegments(720.5, 282, -0.28123220191829523));
        }});
        routes.put("R71", new ArrayList<>(){{
            add(new DonneesSegments(420.5, 390, -0.25933174440068485));
            add(new DonneesSegments(471.5, 375.5, -0.27829965900511133));
            add(new DonneesSegments(522, 360.5, -0.28379410920832787));
            add(new DonneesSegments(573.5, 346.5, -0.27829965900511133));
            add(new DonneesSegments(624, 331, -0.3028848683749714));
            add(new DonneesSegments(675.5, 316, -0.2970642123410428));
            add(new DonneesSegments(724.5, 301.5, -0.28950367196138826));
        }});
        routes.put("R72", new ArrayList<>(){{
            add(new DonneesSegments(743, 187.08928298950195, 0.1586552621864014));
            add(new DonneesSegments(775.5, 218.08928298950195, 1.4711276743037347));
        }});
        routes.put("R73", new ArrayList<>(){{
            add(new DonneesSegments(859, 334, -0.9716210038085897));
        }});
        routes.put("R74", new ArrayList<>(){{
            add(new DonneesSegments(842, 322, -0.9716210038085897));
        }});
        routes.put("R75", new ArrayList<>(){{
            add(new DonneesSegments(930, 285.08928298950195, -0.16514867741462683));
            add(new DonneesSegments(982.5, 277.08928298950195, -0.16183743123842292));
        }});
        routes.put("R76", new ArrayList<>(){{
            add(new DonneesSegments(927, 266.58928298950195, -0.13381097747459075));
            add(new DonneesSegments(980, 259.58928298950195, -0.13909594148207133));
        }});
        routes.put("R77", new ArrayList<>(){{
            add(new DonneesSegments(1529.2999877929688, 556.5, 0.6871242101414391));
        }});
        routes.put("R78", new ArrayList<>(){{
            add(new DonneesSegments(1588, 399.58928298950195, -2.925042348613704));
            add(new DonneesSegments(1545.5, 418.08928298950195, 2.1421338066285234));
            add(new DonneesSegments(1527.5, 467.58928298950195, 1.6724851785579737));
        }});
        routes.put("R79", new ArrayList<>(){{
            add(new DonneesSegments(43.5, 609.8035888671875, 0.523919347685199));
            add(new DonneesSegments(91, 635.3035888671875, 0.5166951387622772));
            add(new DonneesSegments(137, 661.3035888671875, 0.5166951387622772));
            add(new DonneesSegments(183.5, 686.8035888671875, 0.523919347685199));
            add(new DonneesSegments(230.5, 712.3035888671875, 0.4911635982862605));
            add(new DonneesSegments(277, 738.8035888671875, 0.4808872801953358));
        }});
        routes.put("R80", new ArrayList<>(){{
            add(new DonneesSegments(73.5, 488.5, 1.5907936607680473));
            add(new DonneesSegments(73.5, 542, 1.5912016574814347));
            add(new DonneesSegments(42.5, 575.5, 2.90142164572962));
        }});
        routes.put("R81", new ArrayList<>(){{
            add(new DonneesSegments(1611.5, 601.160717010498, 0.3470616035394905));
            add(new DonneesSegments(1663, 615.160717010498, 0.18534794999569476));
            add(new DonneesSegments(1716.5, 621.160717010498, -0.020405330686538086));
            add(new DonneesSegments(1768.5, 613.160717010498, -0.25933174440068485));
            add(new DonneesSegments(1819, 597.160717010498, -0.4076315054576269));
        }});
        routes.put("R82", new ArrayList<>(){{
            add(new DonneesSegments(1780.5, 703.5, -0.32811989501530536));
            add(new DonneesSegments(1824.5, 673, -0.8734055369251891));
            add(new DonneesSegments(1848.5, 625.5, -1.3063079442456642));
        }});
        routes.put("R83", new ArrayList<>(){{
            add(new DonneesSegments(1629.5, 469, 1.2895641248766014));
            add(new DonneesSegments(1654, 516.5, 0.9025069079643125));
            add(new DonneesSegments(1693.5, 550, 0.4911635982862605));
            add(new DonneesSegments(1741.5, 569.5, 0.20963984587421253));
            add(new DonneesSegments(1795.5, 575, -0.02221856532671906));
        }});
        routes.put("R84", new ArrayList<>(){{
            add(new DonneesSegments(1511.5, 666.5892944335938, -0.8991901705411564));
            add(new DonneesSegments(1546, 624.5892944335938, -0.8894981015070477));
        }});
        routes.put("R85", new ArrayList<>(){{
            add(new DonneesSegments(1459, 752.0892944335938, 1.7294515889812982));
            add(new DonneesSegments(1464.5, 804.5892944335938, 1.223734723255406));
            add(new DonneesSegments(1495, 845.0892944335938, 0.6107259643892086));
        }});
        routes.put("R86", new ArrayList<>(){{
            add(new DonneesSegments(867.5, 681.5, 0.9664908199286669));
        }});
        routes.put("R87", new ArrayList<>(){{
            add(new DonneesSegments(851.5, 692.5, 0.9349579032711515));
        }});
        routes.put("R88", new ArrayList<>(){{
            add(new DonneesSegments(1269.5, 482.80357360839844, 1.5907936607680473));
        }});
        routes.put("R89", new ArrayList<>(){{
            add(new DonneesSegments(1248.5, 481.5, 1.633215136790854));
        }});
        routes.put("R90", new ArrayList<>(){{
            add(new DonneesSegments(1276.5, 327.1607131958008, 1.7126933813990606));
            add(new DonneesSegments(1269, 380.1607131958008, 1.7263911547719264));
        }});
        routes.put("R91", new ArrayList<>(){{
            add(new DonneesSegments(1155.7857666015625, 435.0178680419922, -0.020405330686538086));
            add(new DonneesSegments(1208.7857666015625, 432.5178680419922, -0.0407936828678654));
        }});
        routes.put("R92", new ArrayList<>(){{
            add(new DonneesSegments(1753, 893.3035888671875, -0.6273081922757625));
            add(new DonneesSegments(1796.5, 861.8035888671875, -0.6556956262415362));
            add(new DonneesSegments(1838.5, 830.3035888671875, -0.6393859056861729));
            add(new DonneesSegments(63.5, 838.3035888671875, -0.22082876972934834));
            add(new DonneesSegments(115.5, 827.3035888671875, -0.2299044281319047));
            add(new DonneesSegments(166.5, 816.3035888671875, -0.18919902209996817));
            add(new DonneesSegments(219, 805.3035888671875, -0.2347226124851971));
            add(new DonneesSegments(270.5, 793.8035888671875, -0.20963984587421253));
        }});
        routes.put("R93", new ArrayList<>(){{
            add(new DonneesSegments(1741.7999877929688, 877.5, -0.6435011087932844));
            add(new DonneesSegments(1784.7999877929688, 845, -0.6273081922757625));
            add(new DonneesSegments(1828.2999877929688, 813.5, -0.6556956262415362));
            add(new DonneesSegments(59, 820, -0.18534794999569476));
            add(new DonneesSegments(111, 809, -0.22527677921405528));
            add(new DonneesSegments(162.5, 797.5, -0.20131710837464076));
            add(new DonneesSegments(215, 786.5, -0.2053953891897674));
            add(new DonneesSegments(266.5, 775.5, -0.24997862146082245));
        }});
        routes.put("R94", new ArrayList<>(){{
            add(new DonneesSegments(360, 864.8035888671875, -1.8259787174157152));
            add(new DonneesSegments(348, 812.8035888671875, -1.8662371639386166));
        }});
        routes.put("R95", new ArrayList<>(){{
            add(new DonneesSegments(328, 816.8035888671875, 1.3156139361740782));
            add(new DonneesSegments(340.5, 869.3035888671875, 1.3408918986629919));
        }});
        routes.put("R96", new ArrayList<>(){{
            add(new DonneesSegments(111.5, 485.58928298950195, 0.9234041870549677));
            add(new DonneesSegments(143.5, 528.089282989502, 0.9114862584620387));
        }});
        routes.put("R97", new ArrayList<>(){{
            add(new DonneesSegments(127.5, 473.5, 0.9189272124893197));
            add(new DonneesSegments(160, 515.5, 0.9272952180016122));
        }});
        routes.put("R98", new ArrayList<>(){{
            add(new DonneesSegments(155, 440.58928298950195, -0.18534794999569476));
            add(new DonneesSegments(207.5, 431.08928298950195, -0.16859693960942962));
            add(new DonneesSegments(259, 421.58928298950195, -0.19321148384780604));
            add(new DonneesSegments(311.5, 411.58928298950195, -0.18919902209996817));
        }});
        routes.put("R99", new ArrayList<>(){{
            add(new DonneesSegments(151, 421, -0.18534794999569476));
            add(new DonneesSegments(204, 411.5, -0.2053953891897674));
            add(new DonneesSegments(255.5, 401, -0.18919902209996817));
            add(new DonneesSegments(308, 391.5, -0.19739555984988078));
        }});
        routes.put("R100", new ArrayList<>(){{
            add(new DonneesSegments(1667, 454.08929443359375, 1.236059489478082));
            add(new DonneesSegments(1694.5, 499.58929443359375, 0.8734055369251891));
            add(new DonneesSegments(1737.5, 527.0892944335938, 0.28950367196138826));
            add(new DonneesSegments(1789, 528.0892944335938, -0.24497866312686414));
            add(new DonneesSegments(1837, 506.58929443359375, -0.6043055068662296));
            add(new DonneesSegments(1875, 471.58929443359375, -0.7998899024629483));
            add(new DonneesSegments(33.5, 440.08928298950195, -0.28950367196138826));
        }});
        routes.put("R101", new ArrayList<>(){{
            add(new DonneesSegments(1650, 460.5, 1.2120256565243244));
            add(new DonneesSegments(1679, 511.5, 0.844153986113171));
            add(new DonneesSegments(1732, 544.5, 0.28379410920832787));
            add(new DonneesSegments(1793, 546, -0.2754261036960353));
            add(new DonneesSegments(1848, 522, -0.5713374798336268));
            add(new DonneesSegments(1889.5, 483, -0.8131687999908693));
            add(new DonneesSegments(39.5, 457.5, -0.28950367196138826));
        }});
        routes.put("R102", new ArrayList<>(){{
            add(new DonneesSegments(89, 379, 1.5300026439270311));
        }});
        routes.put("R103", new ArrayList<>(){{
            add(new DonneesSegments(68.5, 379.5, 1.5083775167989393));
        }});
        routes.put("R104", new ArrayList<>(){{
            add(new DonneesSegments(121, 405.08928298950195, -0.6998928697192437));
            add(new DonneesSegments(161.5, 369.58928298950195, -0.7248661436153412));
            add(new DonneesSegments(201.5, 336.08928298950195, -0.7086262721276703));
        }});
        routes.put("R105", new ArrayList<>(){{
            add(new DonneesSegments(577.5, 804.8035888671875, -0.2401710078601731));
            add(new DonneesSegments(630.5, 793.3035888671875, -0.22082876972934834));
            add(new DonneesSegments(683, 782.3035888671875, -0.22527677921405528));
            add(new DonneesSegments(735, 771.3035888671875, -0.22527677921405528));
            add(new DonneesSegments(786, 759.8035888671875, -0.2053953891897674));
            add(new DonneesSegments(838, 748.3035888671875, -0.22527677921405528));
        }});
        routes.put("R106", new ArrayList<>(){{
            add(new DonneesSegments(1597.7999877929688, 473, 1.9513027039072617));
            add(new DonneesSegments(1577.7999877929688, 523.5, 1.929566997065469));
        }});
        routes.put("R107", new ArrayList<>(){{
            add(new DonneesSegments(309.5, 475.58928298950195, -1.349967557065548));
            add(new DonneesSegments(340, 429.08928298950195, -0.6202494859828215));
        }});
        routes.put("R108", new ArrayList<>(){{
            add(new DonneesSegments(978.5, 170, 1.044169055361146));
            add(new DonneesSegments(1005.5, 216.5, 0.9994588469612699));
        }});
        routes.put("R109", new ArrayList<>(){{
            add(new DonneesSegments(1080, 252, 0.06241880999595735));
            add(new DonneesSegments(1132.5, 254.5, 0));
            add(new DonneesSegments(1186, 256, 0.019997333973150535));
            add(new DonneesSegments(1239.5, 257.5, 0.0407936828678654));
        }});
        routes.put("R110", new ArrayList<>(){{
            add(new DonneesSegments(1079, 269.5, 0.08314123188844122));
            add(new DonneesSegments(1132, 272, 0.019997333973150535));
            add(new DonneesSegments(1185.5, 274.5, 0.0407936828678654));
            add(new DonneesSegments(1239, 276.5, 0.039978687123290044));
        }});
        routes.put("R111", new ArrayList<>(){{
            add(new DonneesSegments(1044.5, 304, 1.1341691669813554));
            add(new DonneesSegments(1066.5, 353, 1.1341691669813554));
            add(new DonneesSegments(1087.5, 401, 1.171280832795522));
        }});
        routes.put("R112", new ArrayList<>(){{
            add(new DonneesSegments(1119.5, 485.2321472167969, 1.2167988899630005));
            add(new DonneesSegments(1151.5, 531.2321472167969, 0.6316906343389692));
            add(new DonneesSegments(1205, 543.7321472167969, -0.0958591471100132));
        }});
        routes.put("R113", new ArrayList<>(){{
            add(new DonneesSegments(1105.5, 503, 1.223734723255406));
            add(new DonneesSegments(1138, 548, 0.5713374798336268));
            add(new DonneesSegments(1192, 563.5, -0.07982998571223732));
        }});
        routes.put("R114", new ArrayList<>(){{
            add(new DonneesSegments(736, 148, -0.4636476090008061));
            add(new DonneesSegments(789.5, 129, -0.25933174440068485));
            add(new DonneesSegments(842, 119.5, -0.07982998571223732));
            add(new DonneesSegments(896, 122.5, 0.1586552621864014));
        }});
        routes.put("R115", new ArrayList<>(){{
            add(new DonneesSegments(1017.5, 126.5, -0.07827114052127795));
            add(new DonneesSegments(1071.5, 122.5, -0.0407936828678654));
            add(new DonneesSegments(1124, 118, -0.10379234045704283));
            add(new DonneesSegments(1177, 114.5, -0.08314123188844122));
            add(new DonneesSegments(1230, 110.5, -0.08314123188844122));
            add(new DonneesSegments(1283, 107, -0.09966865249116204));
            add(new DonneesSegments(1335, 102, -0.10379234045704283));
        }});
        routes.put("R116", new ArrayList<>(){{
            add(new DonneesSegments(406, 344.5, -0.5144513130590277));
            add(new DonneesSegments(454, 319, -0.5166951387622772));
            add(new DonneesSegments(500, 294.5, -0.4808872801953358));
            add(new DonneesSegments(547, 268.5, -0.5191461142465229));
            add(new DonneesSegments(594, 243, -0.5369107427400456));
            add(new DonneesSegments(640, 217.5, -0.4636476090008061));
        }});
        routes.put("R117", new ArrayList<>(){{
            add(new DonneesSegments(275.5, 329.5, 0.41822432957922906));
            add(new DonneesSegments(323.5, 352.5, 0.4729066036629292));
        }});
        routes.put("R118", new ArrayList<>(){{
            add(new DonneesSegments(1320.2999877929688, 225, -0.9884433806509134));
            add(new DonneesSegments(1350.7999877929688, 180.5, -0.982793723247329));
            add(new DonneesSegments(1380.2999877929688, 136.5, -0.9664908199286669));
        }});
        routes.put("R119", new ArrayList<>(){{
            add(new DonneesSegments(1342.2999877929688, 249.5, -0.4023210978604407));
            add(new DonneesSegments(1390.7999877929688, 228.5, -0.41012734054149097));
            add(new DonneesSegments(1440.2999877929688, 206.5, -0.3805063771123649));
        }});
        routes.put("R120", new ArrayList<>(){{
            add(new DonneesSegments(1270.5, 959.3035888671875, -0.3470616035394905));
            add(new DonneesSegments(1321, 941.3035888671875, -0.3539974368318961));
            add(new DonneesSegments(1370, 922.3035888671875, -0.3539974368318961));
            add(new DonneesSegments(1420, 904.3035888671875, -0.3539974368318961));
            add(new DonneesSegments(1470, 885.8035888671875, -0.372987721800061));
        }});
        routes.put("R121", new ArrayList<>(){{
            add(new DonneesSegments(1278.2999877929688, 978, -0.333945071844894));
            add(new DonneesSegments(1327.7999877929688, 959.5, -0.3347368373168147));
            add(new DonneesSegments(1378.7999877929688, 941.5, -0.3347368373168147));
            add(new DonneesSegments(1427.7999877929688, 923.5, -0.348771003583907));
            add(new DonneesSegments(1477.2999877929688, 904.5, -0.3805063771123649));
        }});
        routes.put("R122", new ArrayList<>(){{
            add(new DonneesSegments(1590.5, 882.3035888671875, 0.1418970546041639));
            add(new DonneesSegments(1644, 891.3035888671875, 0.13909594148207133));
        }});
        routes.put("R123", new ArrayList<>(){{
            add(new DonneesSegments(1588.2999877929688, 902.5, 0.16183743123842292));
            add(new DonneesSegments(1641.2999877929688, 911, 0.17467219900823971));
        }});
        routes.put("R124", new ArrayList<>(){{
            add(new DonneesSegments(1469, 107.08928298950195, 0.07982998571223732));
            add(new DonneesSegments(1521.5, 113.08928298950195, 0.08145204430587097));
            add(new DonneesSegments(1574.5, 119.08928298950195, 0.12184243701991719));
            add(new DonneesSegments(1626.5, 126.08928298950195, 0.20131710837464076));
            add(new DonneesSegments(1676.5, 144.08928298950195, 0.5090708884223831));
            add(new DonneesSegments(1704, 182.58928298950195, 1.4438235371808177));
            add(new DonneesSegments(1695.5, 234.08928298950195, 2.0160973706825827));
        }});
        routes.put("R125", new ArrayList<>(){{
            add(new DonneesSegments(1646, 315.58928298950195, 2.114628783097019));
            add(new DonneesSegments(1634.5, 366.58928298950195, 1.5912016574814347));
        }});
        routes.put("R126", new ArrayList<>(){{
            add(new DonneesSegments(1530, 199.08928298950195, 0.4636476090008061));
            add(new DonneesSegments(1578.5, 222.58928298950195, 0.43662715981354133));
            add(new DonneesSegments(1627, 246.58928298950195, 0.4636476090008061));
        }});
        routes.put("R127", new ArrayList<>(){{
            add(new DonneesSegments(1747.7999877929688, 759, 1.1902899496825317));
            add(new DonneesSegments(1752.7999877929688, 811.5, 1.7848570103587182));
            add(new DonneesSegments(1730.2999877929688, 857.5, 2.2817056999784633));
        }});
        routes.put("R128", new ArrayList<>(){{
            add(new DonneesSegments(1448.5, 140, 0.8424789458037129));
        }});
        routes.put("R129", new ArrayList<>(){{
            add(new DonneesSegments(1685, 397.66071701049805, -0.6435011087932844));
            add(new DonneesSegments(1729.5, 368.16071701049805, -0.5266272714337507));
            add(new DonneesSegments(1776.5, 344.66071701049805, -0.36574730126234445));
            add(new DonneesSegments(1827.5, 328.66071701049805, -0.2401710078601731));
            add(new DonneesSegments(1880.5, 320.66071701049805, -0.07827114052127795));
            add(new DonneesSegments(34.5, 322.58928298950195, 0.18164883000001195));
        }});
        routes.put("R130", new ArrayList<>(){{
            add(new DonneesSegments(134, 308, -0.13909594148207133));
            add(new DonneesSegments(186.5, 302, -0.10168885176307704));
        }});
    }

    static {
        villes = new LinkedHashMap<>();
        villes.put("Winnipeg", new DonneesCerclesPorts(234, 309.79999923706055));
        villes.put("Mexico", new DonneesCerclesPorts (184, 564.7999992370605));
        villes.put("Moskva", new DonneesCerclesPorts (1030, 260.79999923706055));
        villes.put("Tehran", new DonneesCerclesPorts (1105, 443.79999923706055));
        villes.put("Djibouti", new DonneesCerclesPorts (1061, 615.7999992370605));
        villes.put("Lahore", new DonneesCerclesPorts (1263, 432.79999923706055));
        villes.put("Novosibirsk", new DonneesCerclesPorts (1291, 270.79999923706055));
        villes.put("Yakutsk", new DonneesCerclesPorts (1483, 178.79999923706055));
        villes.put("Beijing", new DonneesCerclesPorts (1478, 377.79999923706055));
        villes.put("Port-aux-Francais", new DonneesCerclesPorts (1225, 979));
    }

    static {
        ports = new LinkedHashMap<>();
        ports.put("Cambridge Bay", new DonneesCerclesPorts(337, 82.79999923706055));
        ports.put("Vancouver", new DonneesCerclesPorts(84, 329.79999923706055));
        ports.put("New York", new DonneesCerclesPorts(369, 386.79999923706055));
        ports.put("Los Angeles", new DonneesCerclesPorts(88, 439.79999923706055));
        ports.put("Miami", new DonneesCerclesPorts(306, 523.7999992370605));
        ports.put("Caracas", new DonneesCerclesPorts(382, 625.7999992370605));
        ports.put("Lima", new DonneesCerclesPorts(322, 770.7999992370605));
        ports.put("Rio de Janeiro", new DonneesCerclesPorts(533, 834.7999992370605));
        ports.put("Valparaiso", new DonneesCerclesPorts(362, 916.7999992370605));
        ports.put("Buenos Aires", new DonneesCerclesPorts(472, 917.7999992370605));
        ports.put("Reykjavik", new DonneesCerclesPorts(692, 180.79999923706055));
        ports.put("Edinburgh", new DonneesCerclesPorts(769, 270.79999923706055));
        ports.put("Murmansk", new DonneesCerclesPorts(957, 127.79999923706055));
        ports.put("Hamburg", new DonneesCerclesPorts(873, 286.79999923706055));
        ports.put("Marseille", new DonneesCerclesPorts(808, 365.79999923706055));
        ports.put("Casablanca", new DonneesCerclesPorts(755, 457.79999923706055));
        ports.put("Athina", new DonneesCerclesPorts(941, 408.79999923706055));
        ports.put("Al-Qahira", new DonneesCerclesPorts(1005, 480.79999923706055));
        ports.put("Lagos", new DonneesCerclesPorts(830, 645.7999992370605));
        ports.put("Luanda", new DonneesCerclesPorts(886, 735.4000000953674));
        ports.put("Dar Es Salaam", new DonneesCerclesPorts(1048, 714.4000000953674));
        ports.put("Toamasina", new DonneesCerclesPorts(1104, 800.4000000953674));
        ports.put("Cape Town", new DonneesCerclesPorts(913, 903.4000000953674));
        ports.put("Mumbai", new DonneesCerclesPorts(1255, 549.4000000953674));
        ports.put("Tiksi", new DonneesCerclesPorts(1413, 92.40000009536743));
        ports.put("Hong Kong", new DonneesCerclesPorts(1504, 519.4000000953674));
        ports.put("Bangkok", new DonneesCerclesPorts(1435, 592.4000000953674));
        ports.put("Jakarta", new DonneesCerclesPorts(1474, 704.4000000953674));
        ports.put("Manila", new DonneesCerclesPorts(1572, 578.4000000953674));
        ports.put("Tokyo", new DonneesCerclesPorts(1641, 414.40000009536743));
        ports.put("Petropavlovsk", new DonneesCerclesPorts(1671, 275.40000009536743));
        ports.put("Anchorage", new DonneesCerclesPorts(1854, 223.40000009536743));
        ports.put("Honolulu", new DonneesCerclesPorts(1856, 571.4000000953674));
        ports.put("Port Moresby", new DonneesCerclesPorts(1731, 716.4000000953674));
        ports.put("Darwin", new DonneesCerclesPorts(1629, 752.4000000953674));
        ports.put("Perth", new DonneesCerclesPorts(1530, 887.4000000953674));
        ports.put("Sydney", new DonneesCerclesPorts(1700, 912.4000000953674));
        ports.put("Christchurch", new DonneesCerclesPorts(1806, 960.4000000953674));
    }

    public static class DonneesSegments {
        private final double xCentreSym;
        private final double yCentreSym;
        private final double angle;
        private final double xHautGauche;
        private final double yHautGauche;
        public DonneesSegments(double x, double y, double angle) {
            xCentreSym = x;
            yCentreSym = y;
            this.angle = 180 * angle / Math.PI;
            xHautGauche = (x - largeurRectangle / 2);
            yHautGauche = (y - hauteurRectangle / 2);
        }

        public double getXCentreSym() {
            return xCentreSym;
        }

        public double getYCentreSym() {
            return yCentreSym;
        }

        public double getAngle() {
            return angle;
        }

        public double getXHautGauche() {
            return xHautGauche;
        }

        public double getYHautGauche() {
            return yHautGauche;
        }

    }

    public record DonneesCerclesPorts(double centreX, double centreY) {
    }

    public record DonneesLayout(double layoutX, double layoutY) {
    }

}