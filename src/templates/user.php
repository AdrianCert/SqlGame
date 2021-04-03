<?php
    class user{
        private $id;
        private $nume;
        private $prenume;
        private $photo;

        public function __construct($id, $nume, $prenume, $photo){
            $this->id = $id;
            $this->nume = $nume;
            $this->prenume = $prenume;
            $this->photo = $photo;
        }

        public function getNume(){
            return $this->nume;
        }

        public function getPrenume(){
            return $this->prenume;
        }

        public function getImage(){
            return $this->photo;
        }

        public function __toString(){
            return $this->id . " " . $this->nume . " " . $this->prenume . " " . "<img src=\"" . $this->photo . "\"></img>";
        }
    }
?>