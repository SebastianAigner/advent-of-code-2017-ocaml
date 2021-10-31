#require "str"
;;


let sample = "0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5"

type pipe = int * int

let create_pipes start endings =
    endings
    |> List.map (fun ending -> (start, ending))

let deopt x = List.fold_left (fun acc elem ->
    match elem with
    | Some(e) -> e::acc
    | None -> acc
) [] x |> List.rev


let explode s =
  let rec exp i l =
    if i < 0 then l else exp (i - 1) (s.[i] :: l) in
  exp (String.length s - 1) []

let remove_nondigit = Str.global_replace (Str.regexp "[^0-9 ]+") "";;

let build_graph str =
    str
    |> String.split_on_char '\n'
    |> List.map (fun line -> String.split_on_char ' ' (remove_nondigit line))
    |> List.map (fun line -> line |> List.map(fun elem -> int_of_string_opt elem) |> deopt)
    |> List.map (fun pipedef ->
        create_pipes (List.hd pipedef) (List.tl pipedef)
    )
    |> List.flatten