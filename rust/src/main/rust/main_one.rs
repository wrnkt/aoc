use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn read_lines<P>(file_name: P) -> io::Result<io::Lines<io::BufReader<File>>>
where
    P: AsRef<Path>,
{
    let file = File::open(file_name)?;
    Ok(io::BufReader::new(file).lines())
}

fn first_number(line: String) -> u8 {
    let matches = r"/\d/".matches(&line);
    return matches;
    // println!("{}", line);
}

fn main() {
    let file_path = "../input/1.txt";

    if let Ok(lines) = read_lines(file_path) {
        for line in lines {
            match line {
                Ok(ip) => first_number(ip),
                Err(e) => println!("Couldn't read line."),
            }
        }
    }
}
