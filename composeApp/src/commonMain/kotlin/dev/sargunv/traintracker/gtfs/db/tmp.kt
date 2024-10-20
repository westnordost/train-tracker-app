package dev.sargunv.traintracker.gtfs.db

val shapesCsv = """
499,43.64481,-79.38032,1,0.0
499,43.64072,-79.3989,2,1562.8
499,43.64041,-79.40127,3,1756.6
499,43.64013,-79.40604,4,2141.3
499,43.64063,-79.42191,5,3419.7
499,43.64087,-79.42392,6,3584.2
499,43.64141,-79.42601,7,3762.8
499,43.64212,-79.42778,8,3925.7
499,43.64749,-79.43928,9,5026.4
499,43.64843,-79.44098,10,5198.6
499,43.64951,-79.44252,11,5371.0
499,43.65068,-79.44387,12,5540.8
499,43.67706,-79.46975,13,9137.7
499,43.67845,-79.47135,14,9338.6
499,43.67996,-79.47333,15,9570.7
499,43.68106,-79.47497,16,9750.2
499,43.68332,-79.47902,17,10161.5
499,43.70346,-79.51968,18,14124.4
499,43.70465,-79.52217,19,14363.9
499,43.70541,-79.52414,20,14543.2
499,43.70595,-79.52595,21,14701.2
499,43.70636,-79.52778,22,14855.4
499,43.70675,-79.53037,23,15067.8
499,43.70687,-79.53186,24,15188.4
499,43.70692,-79.53474,25,15419.9
499,43.70437,-79.66235,26,25681.4
499,43.70448,-79.66555,27,25939.1
499,43.70423,-79.66969,28,26272.6
499,43.70395,-79.67723,29,26879.3
499,43.70372,-79.67992,30,27097.0
499,43.70336,-79.68245,31,27304.5
499,43.68685,-79.76448,32,34150.2
499,43.65551,-79.91912,33,47066.7
499,43.65331,-79.92968,34,47950.8
499,43.65234,-79.93306,35,48243.4
499,43.63683,-79.98049,36,52431.6
499,43.63599,-79.98281,37,52640.5
499,43.62914,-79.99911,38,54157.2
499,43.62866,-80.00042,39,54275.6
499,43.62824,-80.00208,40,54416.6
499,43.62799,-80.00352,41,54535.8
499,43.62783,-80.00517,42,54669.9
499,43.6278,-80.00687,43,54806.9
499,43.62813,-80.00973,44,55039.7
499,43.63394,-80.03446,45,57132.7
499,43.63418,-80.03609,46,57266.4
499,43.63423,-80.03718,47,57354.3
499,43.6343,-80.03926,48,57522.0
499,43.63424,-80.04116,49,57674.9
499,43.63411,-80.04292,50,57817.4
499,43.62058,-80.14647,51,66286.5
499,43.62029,-80.14827,52,66434.8
499,43.61986,-80.14996,53,66579.2
499,43.61946,-80.15113,54,66683.3
499,43.61843,-80.15329,55,66890.8
499,43.61782,-80.15426,56,66995.0
499,43.61636,-80.15605,57,67212.4
499,43.58492,-80.18289,58,71322.2
499,43.58368,-80.184,59,71486.5
499,43.58216,-80.18553,60,71695.9
499,43.58039,-80.18754,61,71950.4
499,43.56518,-80.20642,62,74225.1
499,43.56226,-80.21025,63,74673.4
499,43.56111,-80.212,64,74863.0
499,43.56007,-80.21425,65,75078.0
499,43.55934,-80.21623,66,75257.2
499,43.55867,-80.21876,67,75474.6
499,43.55696,-80.22742,68,76197.7
499,43.55625,-80.23007,69,76424.9
499,43.55534,-80.23241,70,76639.1
499,43.55466,-80.23384,71,76777.0
499,43.55367,-80.23557,72,76954.5
499,43.5529,-80.23671,73,77080.0
499,43.54781,-80.24288,74,77833.5
499,43.54371,-80.24721,75,78407.7
499,43.54294,-80.24814,76,78521.1
499,43.54234,-80.24908,77,78622.8
499,43.53777,-80.25821,78,79517.3
499,43.53597,-80.26281,79,79938.3
499,43.53225,-80.27433,80,80955.2
499,43.53112,-80.27745,81,81236.4
499,43.51523,-80.31865,82,84998.7
499,43.48399,-80.39915,83,92361.8
499,43.48321,-80.40146,84,92568.0
499,43.48262,-80.40355,85,92749.2
499,43.47684,-80.42906,86,94905.5
499,43.47627,-80.43128,87,95095.0
499,43.47558,-80.43351,88,95291.1
499,43.45815,-80.48602,89,99950.8
499,43.45375,-80.49896,90,101104.2
499,43.44018,-80.53997,91,104742.5
499,43.43904,-80.54299,92,105017.5
499,43.43776,-80.54584,93,105287.8
499,43.43678,-80.54772,94,105474.8
499,43.42044,-80.57718,95,108468.0
499,43.41964,-80.5789,96,108633.6
499,43.41916,-80.58026,97,108754.8
499,43.41883,-80.58154,98,108864.8
499,43.41859,-80.58291,99,108978.6
499,43.41843,-80.58451,100,109109.3
499,43.4184,-80.60382,101,110668.5
499,43.41826,-80.60612,102,110855.3
499,43.41785,-80.60987,103,111161.6
499,43.41708,-80.61408,104,111512.5
499,43.41581,-80.6189,105,111926.2
499,43.4119,-80.63046,106,112955.7
499,43.41093,-80.63456,107,113304.3
499,43.41035,-80.63874,108,113648.0
499,43.40853,-80.65664,109,115108.6
499,43.40815,-80.65868,110,115278.4
499,43.40764,-80.66059,111,115442.7
499,43.40708,-80.66226,112,115591.4
499,43.40624,-80.66422,113,115775.1
499,43.40535,-80.66591,114,115943.7
499,43.39245,-80.68867,115,118276.5
499,43.39048,-80.69239,116,118647.8
499,43.38963,-80.69435,117,118831.9
499,43.38828,-80.6979,118,119156.3
499,43.36887,-80.75277,119,124088.0
499,43.36767,-80.75629,120,124402.7
499,43.36686,-80.75911,121,124647.4
499,43.36615,-80.76222,122,124910.7
499,43.36548,-80.76644,123,125260.3
499,43.36522,-80.76891,124,125462.0
499,43.36505,-80.7726,125,125760.4
499,43.36677,-80.93158,126,138613.8
499,43.36664,-80.93515,127,138902.5
499,43.36637,-80.93801,128,139135.6
499,43.36601,-80.94061,129,139350.1
499,43.3655,-80.94333,130,139576.3
499,43.36474,-80.94657,131,139852.1
499,43.36437,-80.94851,132,140014.1
499,43.36406,-80.95183,133,140284.9
499,43.36437,-80.97574,134,142217.9
499,43.36444,-80.9769,135,142311.9
499,43.36438,-80.97783,136,142387.1
499,43.36353,-80.98274,137,142795.5
499,43.36155,-80.99133,138,143524.2
499,43.36099,-80.99331,139,143695.9
499,43.36039,-80.99498,140,143846.7
499,43.35954,-80.99685,141,144025.0
499,43.35858,-80.99852,142,144196.7
499,43.27017,-81.13366,143,158900.4
499,43.26909,-81.13514,144,159069.5
499,43.26829,-81.13597,145,159181.5
499,43.26736,-81.1366,146,159297.0
499,43.26665,-81.13694,147,159380.4
499,43.26596,-81.13715,148,159458.4
499,43.26528,-81.13725,149,159534.6
499,43.26454,-81.13723,150,159617.7
499,43.25952,-81.13626,151,160181.3
499,43.25865,-81.13626,152,160277.0
499,43.25766,-81.13643,153,160388.0
499,43.25572,-81.13724,154,160613.5
499,43.25094,-81.13965,155,161180.8
499,43.24966,-81.14001,156,161325.8
499,43.24895,-81.14007,157,161405.1
499,43.24817,-81.14001,158,161491.3
499,43.22612,-81.13546,159,163971.3
499,43.22515,-81.13536,160,164079.5
499,43.22407,-81.13537,161,164199.2
499,43.07522,-81.14913,162,180788.2
499,43.07309,-81.14944,163,181026.2
499,43.07209,-81.14973,164,181140.3
499,43.07066,-81.15031,165,181306.3
499,43.06934,-81.15101,166,181463.9
499,43.01939,-81.17976,167,187489.0
499,43.01752,-81.1811,168,187723.9
499,42.99126,-81.20547,169,191252.7
499,42.99026,-81.20684,170,191410.0
499,42.98983,-81.20759,171,191487.2
499,42.98911,-81.20936,172,191652.1
499,42.98645,-81.2187,173,192467.6
499,42.98636,-81.21945,174,192529.8
499,42.98633,-81.22493,175,192975.1
499,42.98616,-81.22696,176,193141.9
499,42.98544,-81.23117,177,193493.7
499,42.9838,-81.23721,178,194016.9
499,42.9826,-81.24045,179,194313.3
499,42.98199,-81.24256,180,194497.6
499,42.98168,-81.2442,181,194635.1
499,42.97777,-81.2582,182,195854.2
499,42.97728,-81.26058,183,196055.5
499,42.9771,-81.26188,184,196162.5
499,42.97693,-81.26458,185,196383.6
499,42.97692,-81.26617,186,196512.6
499,42.97714,-81.26951,187,196785.6
499,42.97742,-81.27137,188,196940.0
499,42.9902,-81.34246,189,202895.3
499,42.99054,-81.34491,190,203098.1
499,42.99068,-81.34678,191,203250.3
499,42.99072,-81.34855,192,203395.0
499,42.99047,-81.35206,193,203682.0
499,42.99019,-81.35372,194,203820.0
499,42.98971,-81.35573,195,203992.0
499,42.98922,-81.35735,196,204135.2
499,42.98821,-81.35982,197,204365.2
499,42.95446,-81.42974,198,211179.8
499,42.95383,-81.43132,199,211326.1
499,42.95328,-81.43305,200,211479.9
499,42.95272,-81.43543,201,211683.6
499,42.95237,-81.43787,202,211885.5
499,42.94737,-81.48553,203,215804.1
499,42.94712,-81.48843,204,216041.7
499,42.94702,-81.49146,205,216289.0
499,42.94709,-81.49485,206,216565.2
499,42.94742,-81.4987,207,216880.6
499,42.95641,-81.59158,208,224505.6
499,42.95666,-81.59555,209,224829.8
499,42.95666,-81.59866,210,225082.8
499,42.95642,-81.60277,211,225418.7
499,42.94441,-81.74039,212,236698.3
499,42.94425,-81.74325,213,236932.0
499,42.94424,-81.74751,214,237278.8
499,42.94637,-81.94751,215,253558.9
499,42.94833,-82.1604,216,270888.0
499,42.95538,-82.34297,217,285768.1
499,42.9557,-82.34586,218,286005.8
499,42.9561,-82.34796,219,286182.4
499,42.95626,-82.3493,220,286292.5
499,42.95695,-82.36656,221,287699.5
499,42.95684,-82.3686,222,287866.3
499,42.95652,-82.3714,223,288096.8
499,42.95651,-82.37312,224,288236.6
499,42.95709,-82.38892,225,289524.0
""".trimIndent()
