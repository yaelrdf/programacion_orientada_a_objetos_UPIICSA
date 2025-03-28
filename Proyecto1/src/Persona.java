class Persona {
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private int edad;
    private boolean soltero;
    
    //Constructor
    public Persona(String n, String ap, String am, int edad) {
        nombre = n;
        aPaterno = ap;
        aMaterno = am;
        this.edad = edad;
        soltero = true;
    }
    
    //Sobrecarga del constructor
    public Persona(String nombre, String aPaterno, String aMaterno) {
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        edad = 0;
        soltero = true;
    }
    
    public String getNombre() { return nombre; }
    public String getAPaterno() { return aPaterno; }
    public String getAMaterno() { return aMaterno; }
    public int getEdad() { return edad; }
    public boolean isSoltero() { return soltero; }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void setSoltero(boolean ec) {
        soltero = ec;
    }
}