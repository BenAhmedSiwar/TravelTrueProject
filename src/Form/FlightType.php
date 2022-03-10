<?php

namespace App\Form;

use App\Entity\Flight;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FlightType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('villedepart',TextType::class)
            ->add('villearrivee' , TextType::class)
            ->add('datedepart',DateType::class, [
                // renders it as a single text box
                'widget' => 'single_text',
            ])
            ->add('datearrive',DateType::class, [
                // renders it as a single text box
                'widget' => 'single_text',
            ])
            ->add('prix')
            ->add('numplane',TextType::class)
            ->add('compagnie')
            ->add('quantite')
            ->add('image')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Flight::class,
        ]);
    }
}
