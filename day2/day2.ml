let input_string = "
1136	1129	184	452	788	1215	355	1109	224	1358	1278	176	1302	186	128	1148
242	53	252	62	40	55	265	283	38	157	259	226	322	48	324	299
2330	448	268	2703	1695	2010	3930	3923	179	3607	217	3632	1252	231	286	3689
89	92	903	156	924	364	80	992	599	998	751	827	110	969	979	734
100	304	797	81	249	1050	90	127	675	1038	154	715	79	1116	723	990
1377	353	3635	99	118	1030	3186	3385	1921	2821	492	3082	2295	139	125	2819
3102	213	2462	116	701	2985	265	165	248	680	3147	1362	1026	1447	106	2769
5294	295	6266	3966	2549	701	2581	6418	5617	292	5835	209	2109	3211	241	5753
158	955	995	51	89	875	38	793	969	63	440	202	245	58	965	74
62	47	1268	553	45	60	650	1247	1140	776	1286	200	604	399	42	572
267	395	171	261	79	66	428	371	257	284	65	25	374	70	389	51
3162	3236	1598	4680	2258	563	1389	3313	501	230	195	4107	224	225	4242	4581
807	918	51	1055	732	518	826	806	58	394	632	36	53	119	667	60
839	253	1680	108	349	1603	1724	172	140	167	181	38	1758	1577	748	1011
1165	1251	702	282	1178	834	211	1298	382	1339	67	914	1273	76	81	71
6151	5857	4865	437	6210	237	37	410	544	214	233	6532	2114	207	5643	6852
";;
let rec sanitized = function
| [] -> []
| h::t -> (if h <> "" then [h] else []) @ sanitized t
let input_lines = sanitized (String.split_on_char '\n' input_string)

let in_table = List.map (fun line -> sanitized (String.split_on_char '\t' line)) input_lines

let in_table_ints = List.map(fun line -> List.map (fun number -> int_of_string number) line) in_table

let input_table =
    [[5; 1; 9; 5];
    [7; 5; 3];
    [2; 4; 6; 8]];;

let rec dominant_in_list op l = match l with
| [] -> 0
| [x] -> x
| h::t -> if op h (dominant_in_list op t) then h else (dominant_in_list op t) 

let min_in_list = dominant_in_list (<)

let max_in_list = dominant_in_list (>)

let individual_checksums table = List.map (fun row -> max_in_list row - min_in_list row) table

let res_one input = List.fold_left (+) 0 (individual_checksums input)

let is_dividing a b = a mod b = 0 || b mod a == 0

let shift_last_to_first = function
| [] -> []
| [x] -> [x]
| l -> begin match List.rev l with
    | [] -> []
    | h::t -> h :: (List.rev t)
end

let rec shift_n_times n list = match n with
| 0 -> list
| _ -> shift_n_times (n-1) (shift_last_to_first list)

let  get_divisor a list = let l = List.filter (fun elem -> is_dividing a elem) list in
    match l with
    | [x] -> Some(a,x)
    | _ -> None

let rec remove_nth n list = match n with
| 0 -> begin match list with
    | [] -> []
    | [x] -> []
    | h::t -> t
    end
| n -> begin match list with
    | h::t -> h::(remove_nth (n-1) t)
    | _ -> []
    end

let rec headify n list =
match n with
| 0 -> list
| _ -> begin
match list with
| [] -> []
| h::t -> headify (n-1) t @ [h]
end

let find_divisor_pair list = let len = List.length list in 
    let rec impl idx =
        let list = (headify idx list) in
        match list with
        | h::t -> begin match get_divisor h t with
            | Some(x) -> Some(x)
            | None -> impl (idx-1)
            end
        | _ -> None
    in
    impl (len-1)

let rec flatten = function
| [] -> []
| h::t -> begin match h with
    | Some(x) -> x :: (flatten t)
    | None -> (flatten t)
    end

let bigger (x,y) = if x > y then x else y
let smaller (x,y) = if x < y then x else y

let res_two input = List.map (fun row -> (find_divisor_pair row)) input
    |> flatten
    |> List.map (fun pair -> (bigger pair) / (smaller pair))
    |> List.fold_left (+) 0

