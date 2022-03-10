<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\DateType;


class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('prenom')
            ->add('date_naissance' , DateType::class, [
                'widget' => 'single_text', 
                'format' => 'yyyy-MM-dd'
            ])
            ->add('email')
            ->add('photo',FileType::class, array(
                'label'=>'photo',
                'attr'=>[
                    'placeholder'=>'photo',
                    'mapped'=>false,
             
                ],
                'data_class' => null

            ))
            ->add('Save', submitType :: class, [
                'attr' => ['class' => 'btn  btn-warning']
                
                

               
            ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
