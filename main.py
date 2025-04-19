import re
import sys

def parse_java_fields(input_text):
    """Parse Java fields from input text."""
    # Regular expression to match field declarations
    pattern = r'private\s+(\w+(?:<\w+>)?)\s+(\w+);'
    matches = re.findall(pattern, input_text)
    
    fields = []
    for field_type, field_name in matches:
        fields.append({"type": field_type, "name": field_name})
    
    return fields

def generate_constructor(class_name, fields):
    """Generate constructor code."""
    params = ", ".join([f"{field['type']} {field['name']}" for field in fields])
    constructor = f"public {class_name}({params}){{\n"
    
    for field in fields:
        constructor += f"    this.{field['name']}={field['name']};\n"
    
    constructor += "}\n"
    return constructor

def generate_getters_setters(fields):
    """Generate getters and setters code."""
    result = "//Getters y setters\n"
    
    for field in fields:
        field_name = field['name']
        field_type = field['type']
        
        # Capitalize first letter for method name
        capitalized_name = field_name[0].upper() + field_name[1:]
        
        # Getter
        result += f"public {field_type} get{capitalized_name}(){{return {field_name};}}\n"
        
        # Setter
        result += f"public void set{capitalized_name}({field_type} {field_name}){{this.{field_name}={field_name};}}\n"
    
    return result

def process_file(input_file, output_file, class_name):
    """Process input file and write output to file."""
    try:
        with open(input_file, 'r') as f:
            input_text = f.read()
        
        fields = parse_java_fields(input_text)
        
        if not fields:
            print("No valid Java fields found in input file.")
            return
        
        constructor = generate_constructor(class_name, fields)
        getters_setters = generate_getters_setters(fields)
        
        with open(output_file, 'w') as f:
            f.write(constructor + "\n" + getters_setters)
        
        print(f"Successfully generated code in {output_file}")
    except Exception as e:
        print(f"Error: {str(e)}")

if __name__ == "__main__":
    if len(sys.argv) < 4:
        print("Usage: python script.py input_file output_file class_name")
        print("Example: python script.py fields.txt output.java Pelicula")
        sys.exit(1)
    
    input_file = sys.argv[1]
    output_file = sys.argv[2]
    class_name = sys.argv[3]
    
    process_file(input_file, output_file, class_name)