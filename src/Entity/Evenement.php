<?php

namespace App\Entity;

use App\Repository\EvenementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=EvenementRepository::class)
 */
class Evenement
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank( message="Veuillez remplir le vide")
     */

    private $name;

    /**
     * @ORM\Column(type="date")
     * @Assert\GreaterThan ("today")
     * @Assert\Expression(
     *     "this.getdatedebut() < this.getdatefin()",
     *     message="La date fin ne doit pas être antérieur à la date début")
     */
    private $dateDebut;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank( message="Veuillez remplir le vide")
     */
    private $dateFin;

    /**
     * @return mixed
     */
    public function getDateDebut()
    {
        return $this->dateDebut;
    }

    /**
     * @param mixed $dateDebut
     */
    public function setDateDebut($dateDebut): void
    {
        $this->dateDebut = $dateDebut;
    }

    /**
     * @return mixed
     */
    public function getDateFin()
    {
        return $this->dateFin;
    }

    /**
     * @param mixed $dateFin
     */
    public function setDateFin($dateFin): void
    {
        $this->dateFin = $dateFin;
    }

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank( message="Veuillez remplir ce champ vide")
     * @Assert\Positive
     * @Assert\Range(
     *      min = 15,
     *      max=60,
     *      minMessage = "Votre événement doit réunir au moins 15 personnes",
     *      maxMessage="Votre événement a un maximum de 60 personnes"
    )
     */
    private $capacity;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank( message="Veuillez remplir ce champ vide")

     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank( message="Veuillez remplir ce champ vide")
     */
    private $adresse;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image;

    /**
     * @ORM\OneToMany(targetEntity=Comments::class, mappedBy="events", orphanRemoval=true)
     */
    private $comments;


    public function __construct()
    {
        $this->commentaires = new ArrayCollection();
        $this->comments = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }



    public function getCapacity(): ?int
    {
        return $this->capacity;
    }

    public function setCapacity(int $capacity): self
    {
        $this->capacity = $capacity;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }
    public function __toString(){
        return $this->name;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage( $image)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * @return Collection<int, Comments>
     */
    public function getComments(): Collection
    {
        return $this->comments;
    }

    public function addComment(Comments $comment): self
    {
        if (!$this->comments->contains($comment)) {
            $this->comments[] = $comment;
            $comment->setEvents($this);
        }

        return $this;
    }

    public function removeComment(Comments $comment): self
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getEvents() === $this) {
                $comment->setEvents(null);
            }
        }

        return $this;
    }

}
