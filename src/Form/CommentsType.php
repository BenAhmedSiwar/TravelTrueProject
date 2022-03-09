<?php

namespace App\Form;

use App\Entity\Comments;
use Doctrine\DBAL\Types\TextType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommentsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email' , EmailType::class , [ 'label' => ' votre e-mail',
             'attr' =>[
                 'class' => ' form-control'
             ]
            ])
            ->add('nickname' ,  TextType::class , [ 'label' => ' votre pseudo',
                'attr' =>[
                    'class' => ' form-control'
                ]
            ])
            ->add('content' , CKEditorType::class , [ 'label' => ' votre commentaire',
                'attr' =>[
                    'class' => ' form-control'
                ]
            ] )
            ->add('valide', ChekboxType::class)
            ->add('parentid', HiddenType::class, [
                'mapped' => false
            ] )
            ->add('envoyer',SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Comments::class,
        ]);
    }
}
